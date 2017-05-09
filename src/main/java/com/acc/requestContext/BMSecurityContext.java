package com.acc.requestContext;

import com.acc.models.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by melsom.adrian on 25.04.2017.
 */
public class BMSecurityContext implements SecurityContext {

    public static final int ADMIN = 9, SUPERVISOR = 2, USER = 1, STUDENT = 0;

    private final ContextUser contextUser;
    private final User accountUser;

    public BMSecurityContext(final ContextUser contextUser, final User accountUser) {
        this.contextUser = contextUser;
        this.accountUser = accountUser;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return contextUser.hasRole(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }

    public ContextUser getContextUser() {
        return contextUser;
    }

    public User getAccountUser() {
        return accountUser;
    }
}
