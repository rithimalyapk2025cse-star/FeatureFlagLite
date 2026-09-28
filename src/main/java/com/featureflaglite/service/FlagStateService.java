package com.featureflaglite.service;

import com.featureflaglite.dto.StateRequest;
import com.featureflaglite.entity.*;
import com.featureflaglite.exception.ResourceNotFoundException;
import com.featureflaglite.repository.*;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
public class FlagStateService {
    private final FeatureFlagRepository featureFlags;
    private final EnvironmentRepository environments;
    private final FlagStateRepository states;
    private final ChangeLogRepository logs;

    public FlagStateService(FeatureFlagRepository featureFlags, EnvironmentRepository environments,
                            FlagStateRepository states, ChangeLogRepository logs) {
        this.featureFlags = featureFlags;
        this.environments = environments;
        this.states = states;
        this.logs = logs;
    }

    public FlagState setState(Long flagId, String environmentName, StateRequest request) {
        FeatureFlag flag = featureFlags.findById(flagId)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + flagId));
        Environment env = getEnvironment(environmentName);

        FlagState state = states.findByFeatureFlagAndEnvironment(flag, env).orElseGet(FlagState::new);
        boolean old = state.getId() != null ? state.isEnabled() : flag.isDefaultState();

        state.setFeatureFlag(flag);
        state.setEnvironment(env);
        state.setEnabled(request.isEnabled());
        FlagState saved = states.save(state);

        if (old != request.isEnabled()) {
            ChangeLog log = new ChangeLog();
            log.setFeatureFlag(flag);
            log.setEnvironment(env);
            log.setOldState(old);
            log.setNewState(request.isEnabled());
            log.setChangedBy(request.getChangedBy());
            logs.save(log);
        }
        return saved;
    }

    public FlagState getState(Long flagId, String environmentName) {
        FeatureFlag flag = featureFlags.findById(flagId)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + flagId));
        Environment env = getEnvironment(environmentName);
        return states.findByFeatureFlagAndEnvironment(flag, env).orElseGet(() -> {
            FlagState s = new FlagState();
            s.setFeatureFlag(flag);
            s.setEnvironment(env);
            s.setEnabled(flag.isDefaultState());
            return s;
        });
    }

    public boolean evaluate(Long flagId, String environmentName, String userId) {
        FeatureFlag flag = featureFlags.findById(flagId)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + flagId));
        FlagState state = getState(flagId, environmentName);
        if (!state.isEnabled()) return false;
        if (flag.getRolloutPercentage() >= 100) return true;
        if (flag.getRolloutPercentage() <= 0) return false;

        int bucket = hashToBucket(flag.getName() + ":" + environmentName.toUpperCase() + ":" + userId);
        return bucket < flag.getRolloutPercentage();
    }

    public List<ChangeLog> getLogs(Long flagId) {
        FeatureFlag flag = featureFlags.findById(flagId)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + flagId));
        return logs.findByFeatureFlagOrderByChangedAtDesc(flag);
    }

    public List<FlagState> getStates(Long flagId) {
        FeatureFlag flag = featureFlags.findById(flagId)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + flagId));
        return states.findByFeatureFlag(flag);
    }

    public Environment getEnvironment(String name) {
        if (name == null) throw new IllegalArgumentException("Environment is required");
        String normalized = name.toUpperCase();
        if (!Set.of("DEV", "TEST", "PROD").contains(normalized)) {
            throw new IllegalArgumentException("Environment must be DEV, TEST or PROD");
        }
        return environments.findByNameIgnoreCase(normalized)
                .orElseThrow(() -> new ResourceNotFoundException("Environment not initialized: " + normalized));
    }

    private int hashToBucket(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            int number = ((digest[0] & 0xff) << 8) | (digest[1] & 0xff);
            return number % 100;
        } catch (Exception e) {
            return Math.abs(value.hashCode()) % 100;
        }
    }
}
