package com.acc.database.repository;

import com.acc.database.pojo.HbnTag;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 14.02.2017.
 */

public class TagRepository extends AbstractRepository<HbnTag> implements Repository<Tag> {

    public TagRepository(){
        super();
    }

    @Override
    public Tag add(Tag tag){
        HbnTag mappedTag = new HbnTag(
                tag.getName(),
                tag.getDescription(),
                tag.getType());

        int id = super.addEntity(mappedTag);

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
                tag.getType());

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
        List<HbnTag> readData = super.queryFromDb((HqlSpecification) spec);
        List<Tag> result = new ArrayList<>();

        for (HbnTag readTag : readData){
            result.add( new Tag(
                    (int)readTag.getId(),
                    readTag.getTagName(),
                    readTag.getType(),
                    readTag.getDescription()
            ));
        }

        return result;
    }
}
