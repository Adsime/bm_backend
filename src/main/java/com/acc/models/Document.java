package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Document extends HateOAS {

    private int id, author;
    private String title, content, path;
    private List<Group> groups;
    private List<Tag> tags;

    public Document() {}

    public Document(int id, int author, String title, String content, String path, List<Tag> tags) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.path = path;
        this.tags = tags;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toString() {
        return this.toJson();
    }

    public List<Integer> getTagIdList (){
        List<Integer> idList = new ArrayList<>();
        for (Tag tag : tags) idList.add(tag.getId());
        return idList;
    }

    public List<Integer> getGroupsIdList (){
        List<Integer> idList = new ArrayList<>();
        for (Group group : groups) idList.add(group.getId());
        return idList;
    }
}
