package com.acc.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class User implements IBusinessModel {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String enterpriseID;
    private List<Group> groups;
    private List<Tag> tags;

    public User(String firstName, String lastName, String email, String enterpriseID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
    }

    public User(long id, String firstName, String lastName, String email, String enterpriseID, List<Group> groups, List<Tag> tags) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
        this.groups = groups;
        this.tags = tags;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
