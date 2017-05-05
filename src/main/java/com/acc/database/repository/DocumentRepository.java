package com.acc.database.repository;

import com.acc.database.entity.*;
import com.acc.database.specification.*;
import com.acc.models.Document;
import com.acc.models.Group;
import com.acc.providers.Links;

import javax.ejb.Singleton;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (document.getTitle().equals("") || document.getContent().equals("") || document.getAuthor() == -1) {
            throw new IllegalArgumentException("Feil i registrering av fil: \nFyll ut nødvendige felter!");
        }

        HbnDocument mappedDocument;

        try {
            mappedDocument = new HbnDocument(document.getPath(), getAuthor(document.getAuthor()), document.getTitle());
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av fil: \nForfatter med id: " + document.getAuthor() + " finnes ikke");
        }

        try {
            if (document.getGroups() != null){
                mappedDocument.setGroups(getHbnBachelorGroupSet(document.getGroups()));
            }
        }catch (EntityNotFoundException enfe){
            throw new EntityNotFoundException("Feil i registering av fil: \nEn eller flere grupper finnes ikke");
        }

        // TODO: 02.05.2017 if before try?
        try {
            if (document.getTags() != null) mappedDocument.setTags(super.getHbnTagSet(document.getTags()));
        }catch (EntityNotFoundException enfe){
            throw new EntityNotFoundException("Feil i registrering av fil: \nEn eller flere merknader finnes ikke");
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
        HbnDocument mappedDocument;
        try {
            mappedDocument = new HbnDocument(document.getPath(), getAuthor(document.getAuthor()), document.getTitle());
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av fil: \nForfatter med id: " + document.getAuthor() + " finnes ikke");
        }

        try {
            if (document.getGroups() != null) mappedDocument.setGroups(getHbnBachelorGroupSet(document.getGroups()));
        }catch (EntityNotFoundException enfe){
            throw new EntityNotFoundException("Feil i oppdatering av fil: \nEn eller flere grupper finnes ikke");
        }

        try {
            if (document.getTags() != null) mappedDocument.setTags(super.getHbnTagSet(document.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av fil: \nEn eller flere merknader finnes ikke");
        }

        mappedDocument.setId(document.getId());

        try{
            return super.updateEntity(mappedDocument);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av fil: \nOppgave med id: " + document.getId() + " finnes ikke");
        }
    }

    @Override
    public boolean remove(long id) {
        HbnDocument mappedDocument = new HbnDocument();
        mappedDocument.setId(id);

        try{
            return super.removeEntity(mappedDocument);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av fil: \nOppgave med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Document> getQuery(Specification spec) {
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av fil: \nEn eller flere filer finnes ikke");
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
            if (document.getTags() != null) document.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, document.getTagIdList()));
            if (document.getGroups() != null) document.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, document.getGroupsIdList()));
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
            throw new EntityNotFoundException("Feil i henting av fil: \nEn eller flere filer finnes ikke");
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
            if (document.getTags() != null) document.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, document.getTagIdList()));
            if (document.getGroups() != null) document.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, document.getGroupsIdList()));
            result.add(document);
        }
        return result;
    }

    public List<Document> getAssignments(List<String> tags) {
        List<HbnEntity> readData;
        Set<HbnDocument> readDocs = new HashSet<>();
        try{
            readData = super.queryToDb(new GetDocumentAllSpec(tags));
            readData.forEach(item -> readDocs.add((HbnDocument) item));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av fil(oppgaver): \nIngen filer");
        }

        List<Document> result = new ArrayList<>();

        readDocs.forEach(item -> {
            Document document = new Document(
                    (int) item.getId(),
                    (int) item.getUser().getId(),
                    item.getTitle(),
                    "",
                    item.getPath(),
                    item.getTags() != null ? super.toTagList(item.getTags()) : new ArrayList<>());

            List<Integer> authorId = new ArrayList<>(document.getAuthor());
            document.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            if (document.getTags() != null)
                document.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, document.getTagIdList()));
            if (document.getGroups() != null)
                document.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, document.getGroupsIdList()));
            result.add(document);
        });

        return result;
    }

    private HbnUser getAuthor(long authorId){
        return (HbnUser) queryToDb(new GetUserByIdSpec(authorId)).get(0);
    }

    private Set<HbnBachelorGroup> getHbnBachelorGroupSet(List<Group> groups){
        Set<HbnBachelorGroup> hbnBachelorGroups = new HashSet<>();
        for (Group group : groups) {
            hbnBachelorGroups.add((HbnBachelorGroup) super.queryToDb(new GetGroupByIdSpec(group.getId())).get(0));
        }
        return hbnBachelorGroups;
    }

    public int findAuthorId(String enterpriseId){
        try{
            HbnUser foundUser = (HbnUser) queryToDb(new GetUserByEIdSpec(enterpriseId)).get(0);
            return (int)foundUser.getId();
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i opplasting av fil: \nBruker finnes ikke: " + enterpriseId);
        }
    }
}

