package org.example.demo2.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.demo2.dto.request.CreateFeatureRequest;
import org.example.demo2.dto.request.FeatureToggleRequest;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.service.FeatureService;

@Path("/features")
public class FeatureResource {

    FeatureService featureService = new FeatureService();

    @GET
    @RolesAllowed({"user", "admin"})
    @Path("/{environment}")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse createFeature(@PathParam("environment") String environment) {
        return featureService.getFeaturesByEnvironment(environment);
    }

    @POST
    @RolesAllowed({"user", "admin"})
    @Path("/{featureId}/toggle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse toggleFeature(@PathParam("featureId") String featureId, FeatureToggleRequest request) {
        return featureService.toggleFeatures(featureId, request.environmentId, request.newState);
    }

    @POST
    @RolesAllowed({"user", "admin"})
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse createFeature(CreateFeatureRequest request) {
        return featureService.createFeature(request.identifier, request.name, request.description);
    }

    @POST
    @RolesAllowed({"user", "admin"})
    @Path("/{featureId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse deleteFeature(@PathParam("featureId") String featureId) {
        return featureService.deleteFeature(featureId);
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Path("/{featureId}/data")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse getFeature(@PathParam("featureId") String featureId) {
        return featureService.getFeature(featureId);
    }
}
