package com.acc.database;

import com.acc.interfaces.UserService;
import com.acc.models.Tag;
import com.acc.models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class DbHandler implements UserService {

    private static DbHandler db;

    private DbHandler() {

    }

    public DbHandler getInstance() {
        return (db == null) ? db = new DbHandler() : db;
    }

    @Override
    public List<User> getUsers() {
        throw new NotImplementedException();
    }

    @Override
    public User getUser(String id) {
        throw new NotImplementedException();
    }

    @Override
    public List<User> getUserByTag(List<Tag> tags) {
        throw new NotImplementedException();
    }
}
