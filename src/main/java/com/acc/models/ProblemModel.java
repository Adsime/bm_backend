package com.acc.models;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class ProblemModel {
    private String title, body;
    private UserModel author;

    public ProblemModel(String title, String body, UserModel author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
