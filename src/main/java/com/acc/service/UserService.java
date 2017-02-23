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
        return userRepository.getQuery(new GetUserByIdSpec(id)).get(0);
    }

    public List<User> getAllUsers() throws Exception{
        return userRepository.getQuery(new GetUserAllSpec());
    }

    public boolean newUser(User user) throws Exception {
        return userRepository.add(user);
    }

    public boolean deleteUser(int id) {
        return true;
    }

    public boolean updateUser(User user) {
        return userRepository.update(user, user.getId());
    }
}
