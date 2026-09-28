package com.featureflaglite.repository;

import com.featureflaglite.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
    List<ChangeLog> findByFeatureFlagOrderByChangedAtDesc(FeatureFlag featureFlag);
}
