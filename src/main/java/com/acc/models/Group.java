package com.acc.models;

import com.google.gson.Gson;

import javax.json.*;
import java.util.ArrayList;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Group {
    private int id;
    private String path, title, content;
    private ArrayList<Link> links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", id).add("title", title).add("content", content);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Link link : links) {
            //jab.add()
        }
        return job.build();
    }
}
