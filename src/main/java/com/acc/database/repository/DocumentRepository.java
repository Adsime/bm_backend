package com.acc.database.repository;

import com.acc.database.entity.HbnDocument;
import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Document;
import com.acc.providers.Links;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 15.03.2017 NO AUTO INCREMENT OF DOCUMENT
public class DocumentRepository extends AbstractRepository implements Repository<Document> {
        
    public DocumentRepository() {
        super();
    }

    @Override
    public Document add(Document document) throws EntityNotFoundException, IllegalArgumentException{
        if (document.getTitle().equals("") || document.getContent().equals("")) throw new IllegalArgumentException("Feil i registrering av oppgave: \nFyll ut nødvendige felter!");

        //getAuthor() throws EntityNotFoundException
        HbnDocument mappedDocument = new HbnDocument(document.getPath(), getAuthor(document.getAuthor()), document.getTitle());

        try {
             if (document.getTags() != null) mappedDocument.setTags(super.getHbnTagSet(document.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av oppgave: \nEn eller flere merknader finnes ikke");
        }
        long id = super.addEntity(mappedDocument);

        return new Document(
                (int) id,
                document.getAuthor(),
                document.getTitle(),
                document.getContent(),
                document.getPath(),
                document.getTags()
        );
    }

    @Override
    public boolean update(Document document) throws EntityNotFoundException{
        //getAuthor() throws EntityNotFoundException
        HbnDocument mappedDocument = new HbnDocument(document.getPath(), getAuthor(document.getAuthor()), document.getTitle());
        mappedDocument.setId(document.getId());

        try {
            if (document.getTags() != null) mappedDocument.setTags(super.getHbnTagSet(document.getTags()));
        }catch (EntityNotFoundException enf){
           throw new EntityNotFoundException("Feil i oppdatering av oppgave: \nEn eller flere merknader finnes ikke");
        }

        try{
            return super.updateEntity(mappedDocument);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av oppgave: \nOppgave med id: " + document.getId() + " finnes ikke");
        }

    }

    @Override
    public boolean remove(long id) {
        HbnDocument mappedDocument = new HbnDocument();
        mappedDocument.setId(id);

        try{
            return super.removeEntity(mappedDocument);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av oppgave: \nOppgave med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Document> getQuery(Specification spec) {
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av oppgave: \nEn eller flere oppgaver finnes ikke");
        }

        List<Document> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnDocument hbnDocument = (HbnDocument) entity;

            Document document = new Document(
                    (int) hbnDocument.getId(),
                    (int) hbnDocument.getUser().getId(),
                    hbnDocument.getTitle(),
                    "",
                    hbnDocument.getPath(),
                    hbnDocument.getTags() != null ? super.toTagList(hbnDocument.getTags()) : new ArrayList<>()
            );

            List<Integer> authorId = new ArrayList<>(document.getAuthor());
            document.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            if (!document.getTags().isEmpty()) document.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, document.getTagIdList()));
            result.add(document);
        }
        return result;
    }

    @Override
    public List<Document> getMinimalQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av oppgave: \nEn eller flere oppgaver finnes ikke");
        }
        List<Document> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnDocument hbnDocument = (HbnDocument) entity;

            Document document = new Document();
            document.setId((int) hbnDocument.getId());
            document.setTitle(hbnDocument.getTitle());
            document.setTags(
                    hbnDocument.getTags() != null ? super.toTagList(hbnDocument.getTags()) : new ArrayList<>()
            );

            List<Integer> authorId = new ArrayList<>(document.getAuthor());
            document.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            if (!document.getTags().isEmpty()) document.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, document.getTagIdList()));
            result.add(document);
        }
        return result;
    }

    private HbnUser getAuthor(long authorId){
        try {
            return (HbnUser) queryToDb(new GetUserByIdSpec(authorId)).get(0);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering/oppdatering av oppgave: \nForfatter med id: " + authorId + " finnes ikke");
        }
    }
}

