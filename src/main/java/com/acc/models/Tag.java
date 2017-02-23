package com.acc.models;

import com.google.gson.Gson;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Tag implements IBusinessModel {

    private int id;
    private String type, description;

    public Tag(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
