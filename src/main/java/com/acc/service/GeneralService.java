package com.acc.service;

import com.acc.models.Problem;

import java.util.Base64;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class GeneralService {

    public boolean verify(String credentials) {
        try {
            credentials = credentials.substring("Basic ".length()).trim();
            credentials = new String(Base64.getDecoder().decode(credentials));
        } catch (Exception e) {
            return false;
        }
        String pw = credentials.split(":")[1];
        String un = credentials.split(":")[0];
        return true;
    }

    public <T> T getItem(java.lang.Class<T> classOfT) {
        System.out.println(classOfT == Problem.class);
        return null;
    }

}
