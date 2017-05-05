package com.acc.models;

import com.google.gson.Gson;

/**
 * Created by nguyen.duy.j.khac on 04.04.2017.
 */
public class Feedback {

    private String message;
    private int status;

    public Feedback(String message){
        this.message = message;
    }

    public Feedback(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
