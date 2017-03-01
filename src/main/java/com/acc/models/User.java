package com.acc.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class User extends HateOAS {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String enterpriseID;
    private List<Tag> tags;

    public User(String firstName, String lastName, String email, String enterpriseID, List<Tag> tags) {
        this.tags = tags;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
    }

    public User(long id, String firstName, String lastName, String email, String enterpriseID, List<Integer> groupIds, List<Tag> tags) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
