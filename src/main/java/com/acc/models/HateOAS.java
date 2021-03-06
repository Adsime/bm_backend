package com.acc.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by melsom.adrian on 23.02.2017.
 */
public abstract class HateOAS {
    private Map<String, List<Link>> links;

    public void addLinks(String pluralType, List<Link> links) {
        if(this.links == null) {
            this.links = new HashMap<>();
        }
        this.links.put(pluralType, links);
    }

    public Map<String, List<Link>> getLinks() {
        return links;
    }

    public void setLinks(Map<String, List<Link>> links) {
        this.links = links;
    }
}
