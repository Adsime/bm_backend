package com.acc.models;

import com.google.gson.Gson;

/**
 * Created by melsom.adrian on 31.03.2017.
 */
public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
