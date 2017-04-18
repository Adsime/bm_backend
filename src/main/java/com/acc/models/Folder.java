package com.acc.models;

/**
 * Created by melsom.adrian on 15.04.2017.
 */
public class Folder {
    private String name, parent;
    private boolean forced;

    public Folder(String name, String parent, boolean forced) {
        this.name = name;
        this.parent = parent;
        this.forced = forced;
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

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name: '" + name + '\'' +
                ", parent: '" + parent + '\'' +
                ", forced: " + forced +
                '}';
    }
}
