package com.featureflaglite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feature_flags")
public class FeatureFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "default_state", nullable = false)
    private boolean defaultState = false;

    @Min(0)
    @Max(100)
    @Column(name = "rollout_percentage", nullable = false)
    private int rolloutPercentage = 100;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDefaultState() { return defaultState; }
    public void setDefaultState(boolean defaultState) { this.defaultState = defaultState; }
    public int getRolloutPercentage() { return rolloutPercentage; }
    public void setRolloutPercentage(int rolloutPercentage) { this.rolloutPercentage = rolloutPercentage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
