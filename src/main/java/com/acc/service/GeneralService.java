package com.acc.service;

import com.acc.database.repository.IRepository;
import com.acc.models.IBusinessModel;

import java.lang.reflect.Constructor;
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

    public <T> T getItem(java.lang.Class classOfT) {
        try {
            Constructor reco = classOfT.getConstructor();
            IRepository repo = (IRepository) reco.newInstance();
        } catch (IllegalAccessException iae) {
            System.out.println(iae.getStackTrace());
        } catch (InstantiationException ie) {
            System.out.println(ie.getStackTrace());
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }
        return null;
    }

    public boolean addItem(java.lang.Class classOfT, IBusinessModel item) {
        try {
            Constructor reco = classOfT.getConstructor();
            IRepository repo = (IRepository) reco.newInstance();
            return repo.add(item);
        } catch (IllegalAccessException iae) {
            System.out.println(iae.getStackTrace());
        } catch (InstantiationException ie) {
            System.out.println(ie.getStackTrace());
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
        return false;
    }

}
