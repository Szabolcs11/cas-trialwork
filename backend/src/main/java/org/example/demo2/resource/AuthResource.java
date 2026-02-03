package org.example.demo2.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.example.demo2.dto.request.ChangePasswordRequest;
import org.example.demo2.dto.request.LoginRequest;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.service.AuthService;
import org.example.demo2.service.UserService;

@Path("/auth")
public class AuthResource {

    private final UserService userService;
    AuthService authService = new AuthService();

    @Inject
    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse loginUser(LoginRequest request) {
        return authService.loginUser(request.email, request.password);
    }

    @POST
    @Path("/changepassword")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BaseResponse changePassword(@Context SecurityContext securityContext, ChangePasswordRequest request) {
        String email = securityContext.getUserPrincipal().getName();
        return authService.changePassword(email, request.password, request.passwordAgain);
    }

    @Context
    SecurityContext securityContext;
    @GET
    @RolesAllowed({"user", "admin"})
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse whoAmI() {
        String email = securityContext.getUserPrincipal().getName();
        return authService.getUser(email);
    }
}
