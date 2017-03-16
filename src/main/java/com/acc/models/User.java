package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class User extends HateOAS {

    private int id, accessLevel;
    private String firstName;
    private String lastName;
    private String email;
    private String enterpriseID;
    private String accessLevel;
    private List<Tag> tags;

    public User(String firstName, String lastName, String email, String enterpriseID, String accessLevel, List<Tag> tags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
        this.accessLevel =  accessLevel;
        this.tags = tags;
    }

    public User(int id, String firstName, String lastName, String email, String enterpriseID, String accessLevel, List<Tag> tags) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enterpriseID = enterpriseID;
        this.accessLevel =  accessLevel;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Integer> getTagIdList (){
        List<Integer> idList = new ArrayList<>();
        if (tags != null) for (Tag tag : tags) idList.add(tag.getId());
        return idList;
    }
}
