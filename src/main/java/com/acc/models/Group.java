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
    private List<User> students, supervisors;
    private List<Tag> tags;
    private Problem problem;

    public Group(int id, String name, List<User> students, List<User> supervisors, List<Tag> tags, Problem problem) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.supervisors = supervisors;
        this.tags = tags;
        this.problem = problem;
    }

    public Group(int id, String name, List<User> students, List<User> supervisors, Problem problem) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.supervisors = supervisors;
        this.problem = problem;
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

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<User> supervisors) {
        this.supervisors = supervisors;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
