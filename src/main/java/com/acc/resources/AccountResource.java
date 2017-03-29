package com.acc.resources;

import javax.net.ssl.SSLContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Path("accounts")
public class AccountResource {

    @GET
    public String test(SSLContext context) {
        return "hei";
    }
}
