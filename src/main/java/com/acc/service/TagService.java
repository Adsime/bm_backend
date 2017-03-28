package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Tag;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TagService extends GeneralService {

    @Inject
    public TagRepository tagRepository;

    public Tag getTag(int id) {
        try {
            return tagRepository.getQuery(new GetTagByIdSpec((long)id)).get(0);
        } catch (NumberFormatException nfe) {

        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return null;
    }

    public List<Tag> getAllTags() {
        try {
            return tagRepository.getQuery(new GetTagAllSpec());
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return Arrays.asList();
    }

    public List<Tag> queryTags(List<String> names) {
        try {
            List<Tag> tags = tagRepository.getQuery(new GetTagAllSpec());
            if(names.isEmpty()) {
                return tags;
            }
            names.forEach(name -> name = name.toLowerCase());
            tags.removeIf(tag -> !names.contains(tag.getName().toLowerCase()));
            return tags;
        }catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return Arrays.asList();
    }
    
    public Tag newTag(Tag tag) {
        return tagRepository.add(tag);
    }

    public boolean deleteTag(int id) {
        try {
            return tagRepository.remove((long)id);
        } catch (NumberFormatException nfe) {

        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;
    }

    public boolean updateTag(Tag tag) {
        try {
            return tagRepository.update(tag);
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;

    }
}
