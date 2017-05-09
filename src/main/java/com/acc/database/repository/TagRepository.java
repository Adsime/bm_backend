package com.acc.database.repository;

import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnTag;
import com.acc.database.specification.GetTagAllSpec;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;
import org.apache.xmlgraphics.util.io.Finalizable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.IllegalFormatWidthException;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 14.02.2017.
 */

public class TagRepository extends AbstractRepository implements Repository<Tag> {

    public TagRepository(){
        super();
    }
    private final String[] OBLIGATORY_TAG_TYPES = {"student","veileder","bruker","oppgave"};
    private final String[] OBLIGATORY_TAG_NAMES = {"år"};

    @Override
    public Tag add(Tag tag) throws IllegalArgumentException{
        if(tag.getName().equals("") || tag.getType().equals(""))throw new IllegalArgumentException("Feil i registrering av tag: \nFyll ut alle nødvendige felter!");
        if(!isDistinctTagName(tag.getName())) throw new IllegalArgumentException("Feil i registrering av tag: \nTag med navn: " + tag.getName() + " finnes allerde!");

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
            throw new EntityNotFoundException("Feil i oppdatering av tag: \nTag med id: " + tag.getId() + " finnes ikke");
        }
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException, IllegalArgumentException{
        HbnTag mappedTag = (HbnTag)super.queryToDb(new GetTagByIdSpec(id)).get(0);
        if (canDelete(mappedTag)) throw new IllegalArgumentException("Feil i sletting av tag: \nIkke tillat å slette " + mappedTag.getTagName() + "!");

        try{
            return super.removeEntity(mappedTag);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av tag: \nTag med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Tag> getQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try {
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av tag: \nEn eller flere tager finnes ikke");
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
            throw new EntityNotFoundException("Feil i henting av tag: \nEn eller flere tager finnes ikke");
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

    private boolean isDistinctTagName(String tagName){
        List<Tag> existingTags = getQuery(new GetTagAllSpec());
        String name = tagName.toLowerCase();
        return existingTags.stream()
                .filter(exTag->exTag.getName().equals(name))
                .findFirst() == null;
    }

    private boolean canDelete(HbnTag tag){
        for (String name : OBLIGATORY_TAG_NAMES){
            if (tag.getTagName().toLowerCase().equals(name)) return false;
        }
        for (String type: OBLIGATORY_TAG_TYPES){
            if (tag.getType().toLowerCase().equals(type)) return false;
        }
        return true;
    }
}

