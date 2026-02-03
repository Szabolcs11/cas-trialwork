package org.example.demo2.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.demo2.dto.request.CreateEnvironmentRequest;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.dto.response.DataResponse;
import org.example.demo2.service.EnvironmentService;


@Path("/environments")
public class EnvironmentResource {

    EnvironmentService environmentService = new EnvironmentService();

    @GET
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse listEnvironments() {
        return new DataResponse<>(true, environmentService.getEnvironments());
    }

    @POST
    @RolesAllowed("admin")
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse createEnvironment(CreateEnvironmentRequest request) {
        return environmentService.createEnvironment(request.name);
    }

    @POST
    @RolesAllowed("admin")
    @Path("/{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse deleteEnvironment(@PathParam("id") String environmentId) {
        return environmentService.deleteEnvironment(environmentId);
    }
}
