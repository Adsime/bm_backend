package com.acc.service;

import com.acc.database.repository.TagRepository;
import com.acc.models.Tag;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TagService extends GeneralService {

    @Inject
    public TagRepository tagRepository;

    public Tag getTag(int id) {
        //List<Tag> tags = tagRepository.getQuery();
        return null;
    }

    public List<Tag> getAllTags() {
        //List<Tag> tags = tagRepository.getQuery();
        return null;
    }

    public boolean newTag(Tag tag) {
        return true;
    }

    public boolean deleteTag(int id) {
        return true;
    }

    public boolean updateTag(Tag tag) {
        return true;
    }
}
