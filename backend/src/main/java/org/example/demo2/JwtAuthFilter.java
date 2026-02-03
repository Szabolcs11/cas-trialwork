package org.example.demo2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {
    private static final String SECRET = "CHANGE_ME_TO_A_LONG_RANDOM_SECRET_KEY_32_CHARS_MIN";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            requestContext.abortWith(Response.ok().build());
            return;
        }

        if (path.startsWith("auth/login") || path.startsWith("hello")) return;

        String authHeader = requestContext.getHeaderString("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            SecurityContext originalContext =
                    requestContext.getSecurityContext();

            SecurityContext securityContext =
                    new SecurityContext() {

                        @Override
                        public Principal getUserPrincipal() {
                            return () -> email;
                        }

                        @Override
                        public boolean isUserInRole(String r) {
                            return role.equals(r);
                        }

                        @Override
                        public boolean isSecure() {
                            return originalContext.isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return "Bearer";
                        }
                    };

            requestContext.setSecurityContext(securityContext);

            Method method = resourceInfo.getResourceMethod();

            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            if (rolesAllowed == null) return;

            SecurityContext sc = requestContext.getSecurityContext();

            for (String allowedRole : rolesAllowed.value()) {
                if (sc.isUserInRole(allowedRole)) return;
            }
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
