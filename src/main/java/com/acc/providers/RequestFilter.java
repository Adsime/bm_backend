package com.acc.providers;

import com.acc.jsonWebToken.TokenHandler;
import com.acc.requestContext.BMSecurityContext;
import com.acc.requestContext.ContextUser;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Provider
public class RequestFilter implements ContainerRequestFilter {

    public static final String BASIC = "Basic ";
    public static final String BEARER = "Bearer ";

    @Inject
    private TokenHandler tokenHandler;

    @Inject
    private ContextUser contextUser;

    /**
     * Intercepts all incoming requests to ensure they are allowed.
     * @param context ContainerRequestContext.
     * @throws IOException defaults in case an unhandled error occurs.
     */
    @Override
    public void filter(final ContainerRequestContext context) throws IOException {

        Response abort = null;

        //Currently allowing all headers, so pre-flights are approved immediately.
        List<String> preFlightHeaders = context.getHeaders().get("access-control-request-headers");
        if(preFlightHeaders != null) {
            return;
        }
        try {
            List<String> headers = context.getHeaders().get(HttpHeaders.AUTHORIZATION);
            if (headers != null && headers.size() > 0) {
                String authHeader = headers.get(0);
                if (context.getUriInfo().getPath().contains("accounts/login") && authHeader.startsWith(BASIC)) {
                    return;
                }
                String token = authHeader.split(" ")[1];
                if (authHeader.startsWith(BEARER) && tokenHandler.verify(token, context)) {
                    return;
                }
            }
        } catch (LoginException le) {
            abort = Response
                    .status(HttpStatus.FOUND_302)
                    .entity(le.getMessage())
                    .build();
        }
        if(abort == null) {
            abort = Response
                    .status(HttpStatus.UNAUTHORIZED_401)
                    .entity("Unauthorized access attempt.")
                    .build();
        }
        context.abortWith(abort);
    }



}
