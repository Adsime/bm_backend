package com.acc.service;

import com.acc.google.FileHandler;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by melsom.adrian on 23.03.2017.
 */
public class FileService extends GeneralService {

    @Inject
    private FileHandler fileHandler;

    public boolean saveFile(File file, String name, String type, String originalType) {
        String res = fileHandler.uploadAnyFile(file, name, type, originalType);
        if(res != null) return true;
        return false;
    }

}
