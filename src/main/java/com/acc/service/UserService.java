package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.Specification;
import com.acc.models.User;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class UserService extends GeneralService {

    @Inject
    public UserRepository userRepository;



    public User getUser(int id) {
        List<User> users = userRepository.getQuery(new GetUserByIdSpec(id));
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getAllUsers() throws Exception{
        return userRepository.getQuery(new GetUserAllSpec());
    }

    public User newUser(User user) throws Exception {
        return null;
        //return userRepository.add(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.remove((long)id);
    }

    public boolean updateUser(User user) {
        return userRepository.update(user);
    }
}
