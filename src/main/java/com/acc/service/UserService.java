package com.acc.service;
import com.acc.database.pojo.User;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class UserService extends GeneralService {
    public User getUser(int id) {
        return null;
    }

    public List<User> getAllUsers() {
        return null;
    }

    public boolean newUser(User user) {
        return true;
    }

    public boolean deleteUser(int id) {
        return true;
    }

    public boolean updateUser(User user) {
        return true;
    }
}