package com.acc.google;

import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 08.03.2017.
 */
public class GoogleFolder {
    private String name;
    private String id;
    private List<GoogleFolder> children;
    private File folder;

    public GoogleFolder() {
    }

    public GoogleFolder(File folder) {
        this.name = folder.getName();
        this.id = folder.getId();
        this.folder = folder;
    }

    public GoogleFolder(File folder, List<GoogleFolder> googleFolders) {
        this.name = folder.getName();
        this.id = folder.getId();
        this.folder = folder;
        this.children = googleFolders;
    }

    public File getFolder() {
        return this.folder;
    }

    public List<GoogleFolder> getChildren() {
        return children;
    }

    public void setChildren(List<GoogleFolder> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
