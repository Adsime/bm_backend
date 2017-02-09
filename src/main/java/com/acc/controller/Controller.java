package com.acc.controller;

import com.acc.database.DbHandler;
import com.acc.models.Group;

import java.util.Base64;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

public class Controller {

    private DbHandler dbHandler;

    private Controller() {
        dbHandler = DbHandler.getInstance();
    }
    public boolean verify(String credentials) {
        try {
            credentials = credentials.substring("Basic ".length()).trim();
            credentials = new String(Base64.getDecoder().decode(credentials));
        } catch (Exception e) {
            return false;
        }
        String pw = credentials.split(":")[1];
        String un = credentials.split(":")[0];
        System.out.println(un + " " + pw);
        return true;
    }

    public Group findGroup(int id) {
        return new Group();
    }




}