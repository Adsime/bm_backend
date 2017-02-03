package com.acc.controller;

import com.acc.database.DbHandler;

import java.util.Base64;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

public class Controller {

    private static Controller controller;
    private DbHandler dbHandler;

    public static Controller getInstance() {
        return (controller != null) ? controller : new Controller();
    }

    private Controller() {
        dbHandler = DbHandler.getInstance();
    }

    public static Controller getTestController(DbHandler dbHandler) {
        Controller c = new Controller();
        c.dbHandler = dbHandler;
        return c;
    }

    public boolean verify(String credentials) {
        credentials = credentials.substring("Basic ".length()).trim();
        credentials = new String(Base64.getDecoder().decode(credentials));
        String pw = credentials.split(":")[1];
        String un = credentials.split(":")[0];
        System.out.println(un + " " + pw);
        return true;
    }


}