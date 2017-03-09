package com.acc.google;

import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 08.03.2017.
 */
public class Folder {
    private File folder;
    private List<Folder> children;

    public Folder() {
    }

    public Folder(File folder) {
        this.folder = folder;
    }

    public Folder(File folder, List<Folder> folders) {
        this.folder = folder;
        this.children = folders;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public List<Folder> getChildren() {
        return children;
    }

    public void setChildren(List<Folder> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
