package com.acc.providers;

import com.acc.jsonWebToken.TokenHandler;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Provider
public class RequestFilter implements ContainerRequestFilter {

    public static final String BASIC = "Basic ";
    public static final String BEARER = "Bearer ";

    @Inject
    private TokenHandler tokenHandler;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        List<String> a = context.getHeaders().get("access-control-request-headers");
        if(a != null) {
            return;
        }
        List<String> headers = context.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(headers != null && headers.size() > 0) {
            String authHeader = headers.get(0);
            if(context.getUriInfo().getPath().contains("accounts") && authHeader.startsWith(BASIC)) {
                return;
            }
            if(authHeader.startsWith(BEARER) && tokenHandler.verify(authHeader.split(" ")[1])) {
                return;
            }
        }

        Response unauthorizedResponse = Response
                .status(HttpStatus.UNAUTHORIZED_401)
                .entity("Unauthorized access attempt.")
                .build();
        context.abortWith(unauthorizedResponse);
    }


}
