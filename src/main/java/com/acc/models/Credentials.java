package com.acc.models;

import java.util.Base64;

/**
 * Created by melsom.adrian on 30.03.2017.
 */
public class Credentials {
    private String username, password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credentials(String credentials) {
        try {
            credentials = credentials.substring("Basic ".length()).trim();
            credentials = new String(Base64.getDecoder().decode(credentials));
            String[] holder = credentials.split(":");
            this.username = holder[0];
            this.password = holder[1];
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
