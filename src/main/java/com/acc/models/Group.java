package com.acc.models;

import javax.json.*;
import java.util.ArrayList;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class Group implements IBusinessModel {
    private int id;
    private String path, name;
    private ArrayList<Link> links;

    public Group(int id, String path, String name, ArrayList<Link> links) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.links = links;
    }

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

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", id).add("name", name);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Link link : links) {
            jab.add(link.toJson());
        }
        job.add("links", jab);
        return job.build();
    }
}
