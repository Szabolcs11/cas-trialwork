package org.example.demo2.model;

public class EnvironmentFeature {
    private String id;
    private String environmentName;
    private String featureName;
    private Boolean enabled;

    public EnvironmentFeature() {}

    public EnvironmentFeature(String id, String environmentName, String featureName, Boolean enabled) {
        this.id = id;
        this.environmentName = environmentName;
        this.featureName = featureName;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
