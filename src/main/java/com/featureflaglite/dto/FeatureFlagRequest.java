package com.featureflaglite.dto;

import jakarta.validation.constraints.*;

public class FeatureFlagRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private boolean defaultState;

    @Min(0)
    @Max(100)
    private int rolloutPercentage = 100;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDefaultState() { return defaultState; }
    public void setDefaultState(boolean defaultState) { this.defaultState = defaultState; }
    public int getRolloutPercentage() { return rolloutPercentage; }
    public void setRolloutPercentage(int rolloutPercentage) { this.rolloutPercentage = rolloutPercentage; }
}
