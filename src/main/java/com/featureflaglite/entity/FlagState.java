package com.featureflaglite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "flag_states",
       uniqueConstraints = @UniqueConstraint(columnNames = {"feature_flag_id", "environment_id"}))
public class FlagState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_flag_id")
    private FeatureFlag featureFlag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "environment_id")
    private Environment environment;

    @Column(nullable = false)
    private boolean enabled;

    public Long getId() { return id; }
    public FeatureFlag getFeatureFlag() { return featureFlag; }
    public void setFeatureFlag(FeatureFlag featureFlag) { this.featureFlag = featureFlag; }
    public Environment getEnvironment() { return environment; }
    public void setEnvironment(Environment environment) { this.environment = environment; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
