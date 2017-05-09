package com.acc.service;
import com.acc.Exceptions.MultipleChoiceException;
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

    /**
     * Generates a response based on if a user could be queried based on an id.
     * @param id int
     * @return Response
     * @throws InternalServerErrorException any error which has not been thought of
     */
    public Response getUser(int id) throws InternalServerErrorException {
        try {
            List<User> users = userRepository.getQuery(new GetUserByIdSpec(id));
            return Response.ok(users.get(0).toJson()).build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }

    }

    /**
     * Generates an appropriate response based on if users could be queried based on their tags.
     * @param tags List<tags>
     * @param hasAll boolean
     * @return Response
     */
    public Response getUserByTags(List<String> tags, boolean hasAll) {
        try {
            List<User> users = userRepository.getQuery(new GetUserByTagSpec(tags, hasAll));
            return Response.ok(new Gson().toJson(users)).build();
        }catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    /**
     * Generates an appropriate response based on if all the users could be queried from the database.
     * @return Response
     * @throws InternalServerErrorException any error which has not been thought of
     */
    public Response getAllUsers() throws InternalServerErrorException {
        try {
            List<User> users = userRepository.getQuery(new GetUserAllSpec());
            return Response.ok(new Gson().toJson(users)).build();
        } catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    /**
     * Generates an appropriate response based on if a given user is added to the
     * database successfully or not.
     * @param user User
     * @return Response
     * @throws InternalServerErrorException any error which has not been thought of
     */
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

    /**
     * Generates an appropriate response based on if the deletion og a user with a
     * given ID could be done.
     * @param id int
     * @param forced boolean
     * @return Response
     */
    public Response deleteUser(int id, boolean forced) {
        try {
            userRepository.remove((long)id, forced);
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        } catch (MultipleChoiceException mce) {
            return Response.status(mce.getStatus()).entity(mce.getMessage()).build();
        }
    }

    /**
     * Generates an appropriate response based on if a user could be updated with the data
     * given in the incoming user object.
     * @param user User
     * @return Response
     */
    public Response updateUser(User user) {
        try {
            userRepository.update(user);
            return Response.ok().build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }
}
