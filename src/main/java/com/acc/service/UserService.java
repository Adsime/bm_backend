package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.Specification;
import com.acc.models.User;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class UserService extends GeneralService {


    @Inject
    public UserRepository userRepository;

    public UserService() {

    }

    public User getUser(int id) throws InternalServerErrorException {
        try {
            List<User> users = userRepository.getQuery(new GetUserByIdSpec(id));
            return users.isEmpty() ? null : users.get(0);
        } catch (NoSuchEntityException nsee) {
            return null;
        }

    }

    public List<User> getAllUsers() throws InternalServerErrorException {
        try {
            return userRepository.getQuery(new GetUserAllSpec());
        } catch (NoSuchEntityException nsee) {
            return Arrays.asList();
        }
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
