package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Error;
import com.acc.models.Tag;
import org.eclipse.jetty.http.HttpStatus;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

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
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }

    public Response queryTags(List<String> names) {
        try {
            List<Tag> tags = tagRepository.getQuery(new GetTagAllSpec());
            names.forEach(name -> name = name.toLowerCase());
            tags.removeIf(tag -> !names.contains(tag.getName().toLowerCase()));
            return Response.ok(tags.toString()).build();
        }catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }
    
    public Response newTag(Tag tag) {
        try{
            Tag registeredTag = tagRepository.add(tag);
            return Response.status(HttpStatus.CREATED_201).entity(registeredTag).build();
        }catch (IllegalArgumentException iae) {
            Error error = new Error(iae.getMessage());
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).entity(error.toString()).build();
        }

    }

    public Response deleteTag(int id) {
        try {
            tagRepository.remove((long)id);
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        }  catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }
    }

    public Response updateTag(Tag tag) {
        try {
            tagRepository.update(tag);
            return Response.ok().build();
        }catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toString()).build();
        }

    }
}
