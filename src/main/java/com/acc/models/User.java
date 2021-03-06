package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class User extends HateOAS {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String enterpriseID;
    private String accessLevel;
    private List<Tag> tags;
    private String secret = "Hemmelig";
    private List<String> files;

    public User(){}

    public User(String firstName, String lastName, String email, String telephone, String enterpriseID, String accessLevel, List<Tag> tags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.enterpriseID = enterpriseID;
        this.accessLevel =  accessLevel;
        this.tags = tags;
    }

    public User(int id, String firstName, String lastName, String email, String telephone, String enterpriseID, String accessLevel, List<Tag> tags) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<Integer> getTagIdList (){
        List<Integer> idList = new ArrayList<>();
        if (tags != null) for (Tag tag : tags) idList.add(tag.getId());
        return idList;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
