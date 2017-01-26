package com.acc.models;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class Problem {
    private String title, body;
    private User author;

    public Problem(String title, String body, User author) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
