package com.acc.service;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.*;
import com.acc.models.User;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;


import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
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
            return Response.ok(users.get(0).toJson()).build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }

    }

    public Response getUserByTags(List<String> tags, boolean hasAll) {
        try {
            List<User> users = userRepository.getQuery(new GetUserByTagSpec(tags, hasAll));
            return Response.ok(new Gson().toJson(users)).build();
        }catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    public Response getAllUsers() throws InternalServerErrorException {
        try {
            List<User> users = userRepository.getQuery(new GetUserAllSpec());
            return Response.ok(new Gson().toJson(users)).build();
        } catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    public Response newUser(User user) throws InternalServerErrorException {
        try{
            User registeredUser = userRepository.add(user);
            return Response.status(HttpStatus.CREATED_201).entity(registeredUser.toJson()).build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return Response.status(e instanceof EntityNotFoundException
                    ? HttpStatus.BAD_REQUEST_400
                    : HttpStatus.NOT_ACCEPTABLE_406)
                    .entity(e.getMessage()).build();
        }
    }

    public Response deleteUser(int id) {
        try {
            userRepository.remove((long)id);
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    public Response updateUser(User user) {
        try {
            userRepository.update(user);
            return Response.ok().build();
        }  catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }
}
