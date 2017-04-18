package com.acc.models;

/**
 * Created by melsom.adrian on 15.04.2017.
 */
public class Folder {
    private String name, parent;

    public Folder(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name: '" + name + '\'' +
                ", parent:'" + parent + '\'' +
                '}';
    }
}
