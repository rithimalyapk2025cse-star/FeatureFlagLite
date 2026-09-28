package com.featureflaglite.repository;

import com.featureflaglite.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    Optional<Environment> findByNameIgnoreCase(String name);
}
