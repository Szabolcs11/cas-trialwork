package org.example.demo2.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.example.demo2.dto.request.CreateUserRequest;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.service.UserService;


@Path("/users")
public class UserResource {

    UserService userService = new UserService();

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse listUsers() {
        return userService.getUsers();
    }

    @POST
    @RolesAllowed("admin")
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse createUser(CreateUserRequest request) {
        return userService.createUser(request.email, request.password);
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse getUser(@PathParam("id") String userId) {
        return userService.getUser(userId);
    }

    @Context
    SecurityContext securityContext;
    @POST
    @RolesAllowed("admin")
    @Path("/{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse deleteUser(@PathParam("id") String userId) {
        String email = securityContext.getUserPrincipal().getName();
        return userService.deleteUser(email, userId);
    }
}
