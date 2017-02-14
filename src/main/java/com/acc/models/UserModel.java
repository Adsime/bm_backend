package com.acc.models;

import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class UserModel {
    private String id, firstname, lastname, enterpriseID, email;
    private List<TagModel> tagModels;

    public UserModel(String id, String firstname, String lastname, String enterpriseID, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enterpriseID = enterpriseID;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(List<TagModel> tagModels) {
        this.tagModels = tagModels;
    }
}
