package com.acc.providers;

import com.acc.database.repository.UserRepository;
import com.acc.models.User;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Provider
public class RequestFilter implements ContainerRequestFilter {

    public static final String BASIC = "Basic ";
    public static final String BEARER = "Bearer ";

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        List<String> headers = context.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(headers != null && headers.size() > 0) {
            System.out.println(context);
            System.out.println(headers);
            String authHeader = headers.get(0);
            if(authHeader.startsWith(BASIC)) {

            }
        }

        Response unauthorizedResponse = Response
                .status(HttpStatus.UNAUTHORIZED_401)
                .entity("Unauthorized access attempt.")
                .build();
        context.abortWith(unauthorizedResponse);
    }

    private User verify(String credentials) {
        try {
            credentials = credentials.substring("Basic ".length()).trim();
            credentials = new String(Base64.getDecoder().decode(credentials));
        } catch (Exception e) {
            return null;
        }
        String pw = credentials.split(":")[1];
        String un = credentials.split(":")[0];
        User user = userRepository.
        return true;
    }
}
