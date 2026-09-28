package com.featureflaglite.dto;

import jakarta.validation.constraints.NotBlank;

public class StateRequest {
    private boolean enabled;

    @NotBlank
    private String changedBy = "Admin";

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }
}
