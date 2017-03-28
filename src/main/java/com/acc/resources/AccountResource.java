package com.acc.resources;

import org.apache.cxf.rs.security.oauth2.common.AccessTokenRegistration;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.tokens.bearer.BearerAccessToken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Path("accounts")
public class AccountResource {

    @GET
    public String test() {
        AccessTokenRegistration a = new AccessTokenRegistration();
        ServerAccessToken token = new BearerAccessToken(a.getClient(), 600L);
        return token.getRefreshToken();
    }
}
