package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.models.User;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class UserService extends GeneralService {

    @Inject
    private UserRepository userRepository;

    public User getUser(int id) {
        return null; //userRepository.query();
    }

    public List<User> getAllUsers() throws Exception{
        return null;
    }

    public boolean newUser(User user) throws Exception {
        return userRepository.add(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.remove();
    }

    public boolean updateUser(User user) {
        return true;
    }
}
