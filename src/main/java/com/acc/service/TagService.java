package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Tag;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TagService extends GeneralService {

    @Inject
    public TagRepository tagRepository;

    public Tag getTag(int id) {
        return tagRepository.getQuery(new GetTagByIdSpec((long)id)).get(0);
    }

    public List<Tag> getAllTags() {
        return tagRepository.getQuery(new GetTagAllSpec());
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
        return tagRepository.update(tag);
    }
}
