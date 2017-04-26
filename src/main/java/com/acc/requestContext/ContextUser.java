package com.acc.requestContext;

/**
 * Created by melsom.adrian on 25.04.2017.
 */
public class ContextUser {

    private String name, role;

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
