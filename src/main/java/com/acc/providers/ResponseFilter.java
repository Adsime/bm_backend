package com.acc.providers;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Provider
public class ResponseFilter implements ContainerResponseFilter {

    /**
     * Handles the pre-flight requests to the API. Currently allows any IP to make requests.
     * @param requestContext ContainerRequestContext
     * @param responseContext ContainerRequestContext
     * @throws IOException defaults to jersey exception response
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
        responseContext.getHeaders().add("Access-Control-Expose-Headers", "*");
    }

}
