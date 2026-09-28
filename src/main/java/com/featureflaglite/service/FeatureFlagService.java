package com.featureflaglite.service;

import com.featureflaglite.dto.FeatureFlagRequest;
import com.featureflaglite.entity.FeatureFlag;
import com.featureflaglite.exception.ResourceNotFoundException;
import com.featureflaglite.repository.FeatureFlagRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeatureFlagService {
    private final FeatureFlagRepository repository;

    public FeatureFlagService(FeatureFlagRepository repository) {
        this.repository = repository;
    }

    public FeatureFlag create(FeatureFlagRequest request) {
        repository.findByNameIgnoreCase(request.getName()).ifPresent(x -> {
            throw new IllegalArgumentException("Feature name already exists");
        });
        FeatureFlag flag = new FeatureFlag();
        copy(request, flag);
        return repository.save(flag);
    }

    public List<FeatureFlag> findAll() {
        return repository.findAll();
    }

    public FeatureFlag findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found: " + id));
    }

    public FeatureFlag update(Long id, FeatureFlagRequest request) {
        FeatureFlag flag = findById(id);
        copy(request, flag);
        return repository.save(flag);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private void copy(FeatureFlagRequest request, FeatureFlag flag) {
        flag.setName(request.getName());
        flag.setDescription(request.getDescription());
        flag.setDefaultState(request.isDefaultState());
        flag.setRolloutPercentage(request.getRolloutPercentage());
    }
}
