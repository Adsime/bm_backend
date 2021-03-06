package com.acc.models;

import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by melsom.adrian on 02.05.2017.
 */
public class GoogleItem {
    private File file;
    private boolean canDelete;
    private List<Tag> tags;

    public GoogleItem(File file, boolean caDelete) {
        this.file = file;
        this.canDelete = caDelete;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean canDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
