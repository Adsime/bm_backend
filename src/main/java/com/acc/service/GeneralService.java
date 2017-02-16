package com.acc.service;

import com.acc.database.repository.IRepository;
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
        try {
            IRepository repo = (IRepository)classOfT.newInstance();
            System.out.println(classOfT);
            System.out.println("It's alive!" + " " + repo);
        } catch (IllegalAccessException iae) {
            System.out.println(iae.getStackTrace());
        } catch (InstantiationException ie) {
            System.out.println(ie.getStackTrace());
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }

        return null;
    }

}
