package com.featureflaglite.config;

import com.featureflaglite.entity.Environment;
import com.featureflaglite.repository.EnvironmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedEnvironments(EnvironmentRepository repository) {
        return args -> {
            for (String name : new String[]{"DEV", "TEST", "PROD"}) {
                repository.findByNameIgnoreCase(name)
                        .orElseGet(() -> repository.save(new Environment(name)));
            }
        };
    }
}
