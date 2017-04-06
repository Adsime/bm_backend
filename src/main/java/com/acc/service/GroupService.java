package com.acc.service;

import com.acc.database.repository.GroupRepository;
import com.acc.database.specification.GetGroupAllSpec;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.models.Error;
import com.acc.models.Group;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

public class GroupService extends GeneralService{

    @Inject
    public GroupRepository groupRepository;

    public Response getGroup(int id) {
        try {
            Group group = groupRepository.getQuery(new GetGroupByIdSpec((long)id)).get(0);
            return Response.ok(group.toString()).build();
        } catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }

    public Response getAllGroups() {
        try {
            List<Group> groups = groupRepository.getQuery(new GetGroupAllSpec());
            return Response.ok(new Gson().toJson(groups)).build();
        } catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }

    public Response newGroup(Group group) {
        try{
            Group registeredGroup = groupRepository.add(group);
            return Response.status(HttpStatus.CREATED_201).entity(registeredGroup).build();
        } catch (EntityNotFoundException enfe){
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        } catch (IllegalArgumentException iae){
            Error error = new Error(iae.getMessage());
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).entity(error.toString()).build();
        }

    }

    public Response deleteGroup(int id) {
        try {
            groupRepository.remove((long) id);
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        }catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }

    public Response updateGroup(Group group) {
        try {
            groupRepository.update(group);
            return Response.ok().build();
        } catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }
}