package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Feedback;
import com.acc.models.Tag;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TagService extends GeneralService {

    @Inject
    public TagRepository tagRepository;

    public Response getTag(int id) {
        try {
            Tag tag = tagRepository.getQuery(new GetTagByIdSpec((long)id)).get(0);
            return Response.ok(tag.toJson()).build();
        }  catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe).build();
        }
    }

    public Response queryTags(List<String> names) {
        try {
            List<Tag> tags = tagRepository.getQuery(new GetTagAllSpec());
            if(!names.isEmpty()) {
                names.forEach(name -> name = name.toLowerCase());
                tags.removeIf(tag -> !names.contains(tag.getName().toLowerCase()));
            } if(tags.isEmpty()) {
                return Response.status(HttpStatus.NOT_FOUND_404).entity(new Gson().toJson(tags)).build();
            }
            return Response.ok(new Gson().toJson(tags)).build();
        }catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }
    
    public Response newTag(Tag tag) {
        try{
            Tag registeredTag = tagRepository.add(tag);
            return Response.status(HttpStatus.CREATED_201).entity(registeredTag.toJson()).build();
        }catch (IllegalArgumentException iae) {
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).entity(iae.getMessage()).build();
        }

    }

    public Response deleteTag(int id) {
        try {
            tagRepository.remove((long)id);
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        }catch (EntityNotFoundException | IllegalArgumentException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }
    }

    public Response updateTag(Tag tag) {
        try {
            tagRepository.update(tag);
            return Response.ok().build();
        }catch (EntityNotFoundException enfe) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(enfe.getMessage()).build();
        }

    }
}
