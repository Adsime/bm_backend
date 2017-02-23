package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Group extends HateOAS {
    private int id;
    private String name;
    private List<User> users;
    private ArrayList<Link> links;

    public Group(int id, String name, List<User> users, ArrayList<Link> links) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.links = links;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
