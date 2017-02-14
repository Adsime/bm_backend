package com.acc.database;

import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.interfaces.UserService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class DbHandler implements UserService {

    private static DbHandler db;

    private DbHandler() {

    }

    public static DbHandler getInstance() {
        return (db == null) ? db = new DbHandler() : db;
    }

    @Override
    public List<UserModel> getUsers() {
        throw new NotImplementedException();
    }

    @Override
    public UserModel getUser(String id) {
        throw new NotImplementedException();
    }

    @Override
    public List<UserModel> getUserByTag(List<TagModel> tagModels) {
        throw new NotImplementedException();
    }
}
