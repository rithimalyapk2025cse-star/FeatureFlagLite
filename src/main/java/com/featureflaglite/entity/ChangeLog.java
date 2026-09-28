package com.featureflaglite.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_logs")
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_flag_id")
    private FeatureFlag featureFlag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "environment_id")
    private Environment environment;

    @Column(name = "old_state", nullable = false)
    private boolean oldState;

    @Column(name = "new_state", nullable = false)
    private boolean newState;

    @Column(name = "changed_by", nullable = false)
    private String changedBy;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @PrePersist
    public void onCreate() {
        if (changedAt == null) changedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public FeatureFlag getFeatureFlag() { return featureFlag; }
    public void setFeatureFlag(FeatureFlag featureFlag) { this.featureFlag = featureFlag; }
    public Environment getEnvironment() { return environment; }
    public void setEnvironment(Environment environment) { this.environment = environment; }
    public boolean isOldState() { return oldState; }
    public void setOldState(boolean oldState) { this.oldState = oldState; }
    public boolean isNewState() { return newState; }
    public void setNewState(boolean newState) { this.newState = newState; }
    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }
    public LocalDateTime getChangedAt() { return changedAt; }
}
