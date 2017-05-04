package com.acc.models;

import com.google.api.services.drive.model.File;

/**
 * Created by melsom.adrian on 02.05.2017.
 */
public class GoogleItem {
    public File file;
    public boolean caDelete;

    public GoogleItem(File file, boolean caDelete) {
        this.file = file;
        this.caDelete = caDelete;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isCaDelete() {
        return caDelete;
    }

    public void setCaDelete(boolean caDelete) {
        this.caDelete = caDelete;
    }
}
