package com.acc.service;

import com.acc.database.repository.Repository;

import java.lang.reflect.Constructor;
import java.util.Base64;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
@Deprecated
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

}
