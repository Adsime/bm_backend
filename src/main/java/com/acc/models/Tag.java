package com.acc.models;

import com.google.gson.Gson;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Tag extends HateOAS {

    private int id;
    private String name, type, description;

    public Tag() {}

    public Tag(int id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
