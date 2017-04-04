package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.*;
import com.acc.models.Error;
import com.acc.models.User;
import org.eclipse.jetty.http.HttpStatus;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class UserService extends GeneralService {


    @Inject
    public UserRepository userRepository;

    public UserService() {

    }

    public Response getUser(int id) throws InternalServerErrorException {
        try {
            List<User> users = userRepository.getQuery(new GetUserByIdSpec(id));
            return Response.ok(users.get(0).toString()).build();
        }  catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }

    }

    public List<User> getUserByTags(List<String> tags, boolean hasAll) {
        try {
            List<User> users = userRepository.getQuery(new GetUserByTagSpec(tags, hasAll));
            return users.isEmpty() ? null : users;
        }catch (EntityNotFoundException enfe) {

        }
        return null;
    }

    public List<User> getAllUsers() throws InternalServerErrorException {
        try {
            return userRepository.getQuery(new GetUserAllSpec());
        } catch (NoSuchEntityException nsee) {

        }  catch (EntityNotFoundException enfe) {

        }
        return Arrays.asList();
    }

    public User newUser(User user) throws InternalServerErrorException {
        return userRepository.add(user);
    }

    public boolean deleteUser(int id) {
        try {
            return userRepository.remove((long)id);
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;

    }

    public boolean updateUser(User user) {
        try {
            return userRepository.update(user);
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;

    }
}
