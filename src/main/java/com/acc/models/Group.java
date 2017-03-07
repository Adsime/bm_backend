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
    private Problem problem;

    public Group(int id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
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

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public List<Integer> getUserIdList(){
        List<Integer> idList = new ArrayList<>();

        // TODO: 06.03.2017 if users is initialized?
        for (User user : users) idList.add(user.getId());
        return idList;
    }
}
