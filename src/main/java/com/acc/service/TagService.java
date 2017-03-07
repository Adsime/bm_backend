package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Tag;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
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
            return null;
        } catch (NoSuchEntityException nsee) {
            return null;
        }
    }

    public List<Tag> getAllTags() {
        try {
            return tagRepository.getQuery(new GetTagAllSpec());
        } catch (NoSuchEntityException nsee) {
            return Arrays.asList();
        }

    }
    
    public Tag newTag(Tag tag) {
        return tagRepository.add(tag);
    }

    public boolean deleteTag(int id) {
        try {
            return tagRepository.remove((long)id);
        } catch (NumberFormatException nfe) {
            return false;
        } catch (NoSuchEntityException nsee) {
            return false;
        }
    }

    public boolean updateTag(Tag tag) {
        try {
            return tagRepository.update(tag);
        } catch (NoSuchEntityException nsee) {
            return false;
        }

    }
}
