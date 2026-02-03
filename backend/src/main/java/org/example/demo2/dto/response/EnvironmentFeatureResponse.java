package org.example.demo2.dto.response;
import org.example.demo2.model.EnvironmentFeature;

import java.util.List;

public class EnvironmentFeatureResponse<T> extends DataResponse {

    private final List<EnvironmentFeature> environmentFeature;

    public EnvironmentFeatureResponse(boolean status, T data, List<EnvironmentFeature> environmentFeature) {
        super(status, data);
        this.environmentFeature = environmentFeature;
    }

    public List<EnvironmentFeature> getEnvironmentFeature() {
        return environmentFeature;
    }
}
