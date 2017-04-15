package com.acc.google;

import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 08.03.2017.
 */
public class googleFolder {
    private File folder;
    private List<googleFolder> children;

    public googleFolder() {
    }

    public googleFolder(File folder) {
        this.folder = folder;
    }

    public googleFolder(File folder, List<googleFolder> googleFolders) {
        this.folder = folder;
        this.children = googleFolders;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public List<googleFolder> getChildren() {
        return children;
    }

    public void setChildren(List<googleFolder> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
