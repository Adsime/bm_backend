package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.*;
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

    public List<User> getUserByTags(List<String> tags, boolean hasAll) {
        try {
            List<User> users = userRepository.getQuery(new GetUserByTagSpec(tags, hasAll));
            return users.isEmpty() ? null : users;
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

    public User newUser(User user) throws InternalServerErrorException {
        return userRepository.add(user);
    }

    public boolean deleteUser(int id) {
        try {
            return userRepository.remove((long)id);
        } catch (NoSuchEntityException nsee) {
            return false;
        }

    }

    public boolean updateUser(User user) {
        try {
            return userRepository.update(user);
        } catch (NoSuchEntityException nsee) {
            return false;
        }

    }
}
