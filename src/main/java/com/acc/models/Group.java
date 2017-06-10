package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Group extends HateOAS {
    private int id;
    private String name, assignment;
    private List<User> students, supervisors;
    private List<Document> documents;
    private List<Tag> tags;

    public Group() {}

    public Group(int id, String name, List<User> students, List<User> supervisors) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.supervisors = supervisors;
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public List<Integer> getTagIdList (){
        List<Integer> idList = new ArrayList<>();
        if (tags != null) for (Tag tag : tags) idList.add(tag.getId());
        return idList;
    }

    public List<Integer> getDocumentIdList (){
        List<Integer> idList = new ArrayList<>();
        if (documents != null) for (Document document : documents) idList.add(document.getId());
        return idList;
    }
}
