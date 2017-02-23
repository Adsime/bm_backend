package com.acc.models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Problem extends HateOAS {

    private int id, author;
    private String title, content, path;
    private ArrayList<Link> links;

    public Problem(int id, int author, String title, String content, String path, ArrayList<Link> links) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.path = path;
        this.links = links;
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

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", path='" + path + '\'' +
                ", links=" + links +
                '}';
    }
}
