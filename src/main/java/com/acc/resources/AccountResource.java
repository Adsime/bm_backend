package com.acc.resources;

import com.acc.jsonWebToken.LoginHandler;
import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.User;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.awt.geom.RectangularShape;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Path("accounts")
public class AccountResource {

    @Context
    private HttpHeaders headers;

    @Inject
    private LoginHandler loginHandler;

    @Inject
    private TokenHandler tokenHandler;

    @GET
    public Response login() {
        User user = loginHandler.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0));
        if(user != null) {
            String token = tokenHandler.generateToken(user);
            return Response.ok(token).build();
        }
        return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }
}
