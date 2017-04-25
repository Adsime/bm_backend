package com.acc.requestContext;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by melsom.adrian on 25.04.2017.
 */
public class BMSecurityContext implements SecurityContext {

    public static final int ADMIN = 9, SUPERVISOR = 2, USER = 1, STUDENT = 0;

    private final ContextUser user;

    public BMSecurityContext(final ContextUser user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.hasRole(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
