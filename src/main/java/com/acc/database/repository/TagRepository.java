package com.acc.database.repository;

import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnTag;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 14.02.2017.
 */

public class TagRepository extends AbstractRepository implements Repository<Tag> {

    public TagRepository(){
        super();
    }

    @Override
    public Tag add(Tag tag){
        HbnTag mappedTag = new HbnTag(
                tag.getName(),
                tag.getDescription(),
                tag.getType());

        int id = (int)super.addEntity(mappedTag);

        return new Tag(
                id,
                tag.getName(),
                tag.getType(),
                tag.getDescription()
        );
    }

    @Override
    public boolean update(Tag tag) {
        HbnTag mappedTag = new HbnTag(
                tag.getName(),
                tag.getDescription(),
                tag.getType()
        );
        mappedTag.setId(tag.getId());
        return super.updateEntity(mappedTag);
    }

    @Override
    public boolean remove(long id) {
        HbnTag mappedTag = new HbnTag();
        mappedTag.setId(id);
        return super.removeEntity(mappedTag);
    }

    @Override
    public List<Tag> getQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<Tag> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnTag hbnTag = (HbnTag) entity;
            result.add( new Tag(
                    (int)hbnTag.getId(),
                    hbnTag.getTagName(),
                    hbnTag.getType(),
                    hbnTag.getDescription()
            ));
        }
        return result;
    }

    @Override
    public List<Tag> getMinimalQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<Tag> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnTag hbnTag = (HbnTag) entity;
            Tag tag = new Tag();
            tag.setId((int)hbnTag.getId());
            tag.setName(hbnTag.getTagName());
            tag.setType(hbnTag.getType());
        }
        return result;
    }
}
