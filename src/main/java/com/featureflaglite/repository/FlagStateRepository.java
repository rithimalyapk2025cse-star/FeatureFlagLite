package com.featureflaglite.repository;

import com.featureflaglite.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface FlagStateRepository extends JpaRepository<FlagState, Long> {
    Optional<FlagState> findByFeatureFlagAndEnvironment(FeatureFlag featureFlag, Environment environment);
    List<FlagState> findByFeatureFlag(FeatureFlag featureFlag);
}
