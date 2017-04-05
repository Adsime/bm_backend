package com.acc.models;

import com.google.gson.Gson;

/**
 * Created by nguyen.duy.j.khac on 04.04.2017.
 */
public class Error {

    private String message;

    public Error(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
