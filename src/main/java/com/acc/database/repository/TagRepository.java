package com.acc.database.repository;

import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnTag;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;

import javax.persistence.EntityNotFoundException;
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
    public Tag add(Tag tag) {
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
    public boolean update(Tag tag) throws EntityNotFoundException{
        HbnTag mappedTag = new HbnTag(
                tag.getName(),
                tag.getDescription(),
                tag.getType()
        );
        mappedTag.setId(tag.getId());

        try {
            return super.updateEntity(mappedTag);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering: \nMerknad med id: " + tag.getId() + " finnes ikke");
        }
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException{
        HbnTag mappedTag = new HbnTag();
        mappedTag.setId(id);
        try{
            return super.removeEntity(mappedTag);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering: \nMerknad med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Tag> getQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try {
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting: \nEn eller flere merknader finnes ikke");
        }

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
    public List<Tag> getMinimalQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try {
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting: \nEn eller flere merknader finnes ikke");
        }

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
