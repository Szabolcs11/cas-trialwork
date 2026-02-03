package org.example.demo2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.demo2.model.Environment;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.dto.response.MessageResponse;
import org.example.demo2.repository.EnvironmentRepository;

import java.util.List;

@ApplicationScoped
public class EnvironmentService {

    EnvironmentRepository environmentRepository = new EnvironmentRepository();

    public List<Environment> getEnvironments() {
        return environmentRepository.getEnvironments();
    }

    public BaseResponse createEnvironment(String name) {
        if (name.isEmpty()) return new MessageResponse(false, "Name field is empty");
        String id = environmentRepository.createEnvironment(name);
        if (id.equals("E-409")) return new MessageResponse(false, "Name already exist");
        if (id.equals("E-500")) return new MessageResponse(false, "Unexpected error");

        boolean created = environmentRepository.createEnvironmentFeatures(id);
        if (!created) return new MessageResponse(false, "Unexpected error");

        return new MessageResponse(true, "Successfully created environment");
    }

    public BaseResponse deleteEnvironment(String id) {
        if (id.isEmpty()) return new MessageResponse(false, "id field is empty");
        boolean deleted = environmentRepository.deleteEnvironment(id);
        if (!deleted) return new MessageResponse(false, "Unexpected error");
        return new MessageResponse(true, "Successfully deleted environment");
    }
}
