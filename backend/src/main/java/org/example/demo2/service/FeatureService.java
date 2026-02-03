package org.example.demo2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.demo2.dto.response.EnvironmentFeatureResponse;
import org.example.demo2.model.Environment;
import org.example.demo2.model.EnvironmentFeature;
import org.example.demo2.model.Feature;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.dto.response.DataResponse;
import org.example.demo2.dto.response.MessageResponse;
import org.example.demo2.repository.EnvironmentRepository;
import org.example.demo2.repository.FeatureRepository;
import java.util.List;

@ApplicationScoped
public class FeatureService {

    FeatureRepository featureRepository = new FeatureRepository();
    EnvironmentRepository environmentRepository = new EnvironmentRepository();

    public BaseResponse getFeaturesByEnvironment(String environment) {
        List<Feature> featureList = featureRepository.getFeaturesByEnvironment(environment);
        if (featureList.isEmpty()) return new MessageResponse(false, "No environment found with the provided name");
        return new DataResponse<>(true, featureList);
    }

    public BaseResponse toggleFeatures(String featureId, String environmentId, boolean status) {
        boolean toggled = featureRepository.toggleFeatures(featureId, environmentId, status);
        if (!toggled) return new MessageResponse(false, "Unexpected error");
        return new MessageResponse(true, "Feature status changed");
    }

    public BaseResponse createFeature(String identifier, String name, String description) {
        if (identifier.isEmpty()) return new MessageResponse(false, "Identifier field is empty");
        if (name.isEmpty()) return new MessageResponse(false, "Name field is empty");
        if (description.isEmpty()) return new MessageResponse(false, "Description field is empty");

        String insertId = featureRepository.createFeature(identifier, name, description);
        if (insertId.equals("E-409")) return new MessageResponse(false, "Name already exist");
        if (insertId.equals("E-500")) return new MessageResponse(false, "Unexpected error");

        List<Environment> environments = environmentRepository.getEnvironments();
        if (environments.isEmpty()) return new MessageResponse(false, "Unexpected error");

        boolean created = featureRepository.insertFeatureToEnvironments(insertId, environments);
        if (!created) return new MessageResponse(false, "Unexpected error");

        return new MessageResponse(true, "Successfully created feature");
    }

    public BaseResponse deleteFeature(String featureId) {
        if (featureId.isEmpty()) return new MessageResponse(false, "id is empty");

        boolean deleted = featureRepository.deleteFeature(featureId);
        if (!deleted) return new MessageResponse(false, "Unexpected error");

        return new MessageResponse(true, "Successfully deleted feature");
    }

    public BaseResponse getFeature(String featureId) {
        if (featureId.isEmpty()) return new MessageResponse(false, "Id is empty");

        Feature feature = featureRepository.getFeature(featureId);

        List<EnvironmentFeature> environmentFeature = featureRepository.getFeatureEnvironments(featureId);

        return new EnvironmentFeatureResponse<Feature>(true, feature, environmentFeature);
    }
}
