package com.acc.models;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class Link implements IBusinessModel {

    private String rel, href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("rel", rel).add("href", href);
        return job.build();
    }
}
