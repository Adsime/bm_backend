package com.acc.database.repository;

import com.acc.database.entity.*;
import com.acc.database.specification.*;
import com.acc.models.*;
import com.acc.providers.Links;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository implements Repository<Group> {

    public GroupRepository(){
        super();
    }

    @Override
    public Group add(Group group) throws EntityNotFoundException, IllegalArgumentException{
        if(group.getName().equals("")) throw new IllegalArgumentException("Feil i registrering av gruppe: \nFyll ut nødvendige felter!");

        HbnBachelorGroup mappedGroup = new HbnBachelorGroup(group.getName());

        try {
            if (group.getTags() != null) mappedGroup.setTags(super.getHbnTagSet(group.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av gruppe: \nEn eller flere merknader finnes ikke");
        }

        try {
            if (group.getDocuments() != null) mappedGroup.setDocuments(getHbnDocuments(group.getDocuments()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av gruppe: \nOppgave med id: " + group.getDocuments().get(0).getId() + " finnes ikke");
        }
        
        Set<HbnUser> groupAssociates = new HashSet<>();
        // TODO: 18.04.2017 try catch for supervisor
        if (group.getSupervisors() != null) groupAssociates.addAll(getHbnSupervisorSet(group.getSupervisors()));
        if (group.getStudents() != null) groupAssociates.addAll(addIfNotExist(group.getStudents()));
        mappedGroup.setUsers(groupAssociates);

        long id = super.addEntity(mappedGroup);

        return new Group(
                (int)id,
                group.getName(),
                group.getStudents(),
                group.getSupervisors()
        );
    }

    @Override
    public boolean update(Group group) throws EntityNotFoundException{
        Set<HbnUser> groupAssociates = new HashSet<>();
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup(group.getName());
        hbnBachelorGroup.setId(group.getId());

        try {
            if (group.getTags() != null) hbnBachelorGroup.setTags(super.getHbnTagSet(group.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av gruppe: \nEn eller flere merknader finnes ikke");
        }

        try {
            if (group.getDocuments() != null) hbnBachelorGroup.setDocuments(getHbnDocuments(group.getDocuments()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av gruppe: \nEn eller flere oppgaver finnes ikke");
        }

        // TODO: 18.04.2017 try catch for supervisor
        if (group.getSupervisors() != null) groupAssociates.addAll(getHbnSupervisorSet(group.getSupervisors()));
        if (group.getStudents() != null) groupAssociates.addAll(addIfNotExist(group.getStudents()));
        hbnBachelorGroup.setUsers(groupAssociates);

        return super.updateEntity(hbnBachelorGroup);
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException{
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup();
        hbnBachelorGroup.setId(id);
        try {
            return super.removeEntity(hbnBachelorGroup);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av gruppe: \nGruppe med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Group> getQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
         }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av gruppe: \nEn eller flere grupper finnes ikke");
        }
        List<Group> result = new ArrayList<>();

        for (HbnEntity entity : readData ){
            HbnBachelorGroup hbnBachelorGroup = (HbnBachelorGroup) entity;

            List<Document> groupDocuments = new ArrayList<>();
            Set<HbnDocument> hbnDocuments = hbnBachelorGroup.getDocuments();
            if (!hbnDocuments.isEmpty()){

                Document document = null;
                for (HbnDocument hbnDocument : hbnDocuments)
                document = new Document(
                        (int) hbnDocument.getId(),
                        (int) hbnDocument.getUser().getId(),
                        "",
                        "",
                        hbnDocument.getPath(),
                        super.toTagList(hbnDocument.getTags()
                ));
                if (document != null) {
                    List<Integer> authorId = new ArrayList<>(document.getAuthor());
                    document.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
                }
                groupDocuments.add(document);
            }

            Group group = new Group(
                    (int) hbnBachelorGroup.getId(),
                    hbnBachelorGroup.getName(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            group.setDocuments(groupDocuments);

            for (HbnUser hbnUser : hbnBachelorGroup.getUsers()){
                User user = new User(
                        (int)hbnUser.getId(),
                        hbnUser.getFirstName(),
                        hbnUser.getLastName(),
                        hbnUser.getEmail(),
                        hbnUser.getTelephone(),
                        hbnUser.getEnterpriseId(),
                        hbnUser.getAccessLevel(),
                        super.toTagList(hbnUser.getTags())
                );

                if (hasStudentTag(hbnUser.getTags())) group.getStudents().add(user);
                else group.getSupervisors().add(user);
            }

            List<Integer> studentIdList = new ArrayList<>();
            List<Integer> supervisorIdList = new ArrayList<>();
            for (HbnUser hbnUser: hbnBachelorGroup.getUsers()){
                if(hasStudentTag(hbnUser.getTags()))studentIdList.add((int)hbnUser.getId());
                else supervisorIdList.add((int)hbnUser.getId());
            }
            group.addLinks(Links.STUDENTS, Links.generateLinks(Links.STUDENT, studentIdList));
            group.addLinks(Links.SUPERVISORS, Links.generateLinks(Links.SUPERVISOR, supervisorIdList));
            group.addLinks(Links.DOCUMENTS, Links.generateLinks(Links.DOCUMENT, group.getDocumentIdList()));

            if (hbnBachelorGroup.getTags() != null) {
                group.setTags(super.toTagList(hbnBachelorGroup.getTags()));
                group.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, group.getTagIdList()));
            }

            result.add(group);
        }
        return result;
    }

    // TODO: 18.04.2017 ASSIGN VALUE TO ASSIGNMET FOR GROUP MODEL
    @Override
    public List<Group> getMinimalQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av gruppe: \nEn eller flere grupper finnes ikke");
        }

        List<Group> result = new ArrayList<>();

        for (HbnEntity entity : readData ){
            HbnBachelorGroup hbnBachelorGroup = (HbnBachelorGroup) entity;

            Group group = new Group();
            group.setId((int)hbnBachelorGroup.getId());
            group.setName(hbnBachelorGroup.getName());

            List<Integer> studentIdList = new ArrayList<>();
            List<Integer> supervisorIdList = new ArrayList<>();
            for (HbnUser hbnUser: hbnBachelorGroup.getUsers()){
                if(hasStudentTag(hbnUser.getTags()))studentIdList.add((int)hbnUser.getId());
                else supervisorIdList.add((int)hbnUser.getId());
            }

            group.addLinks(Links.STUDENTS, Links.generateLinks(Links.STUDENT, studentIdList));
            group.addLinks(Links.SUPERVISORS, Links.generateLinks(Links.SUPERVISOR, supervisorIdList));
            group.addLinks(Links.DOCUMENTS, Links.generateLinks(Links.DOCUMENT, group.getDocumentIdList()));

            if (hbnBachelorGroup.getTags() != null) {
                group.setTags(super.toTagList(hbnBachelorGroup.getTags()));
                group.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, group.getTagIdList()));
            }

            Set<HbnDocument> groupDocuments = hbnBachelorGroup.getDocuments();
            group.setAssignment(getAssignment(groupDocuments));

            result.add(group);
        }
        return result;
    }

    private Set<HbnDocument> getHbnDocuments(List<Document> documents){
        Set<HbnDocument> hbnDocuments = new HashSet<>();
        for (Document document : documents) {
            hbnDocuments.add((HbnDocument) super.queryToDb(new GetDocumentByIdSpec(document.getId())).get(0));
        }
        return hbnDocuments;
    }

    private boolean hasStudentTag(Set<HbnTag> tags){
        for (HbnTag hbnTag : tags){
            if (hbnTag.getTagName().toLowerCase().equals("student")) return true;
        }
        return false;
    }

    private Set<HbnUser> getHbnSupervisorSet(List<User> users){
        Set<HbnUser> supervisorSet = new HashSet<>();
        for (User supervisors : users){
            supervisorSet.add((HbnUser) super.queryToDb(new GetUserByIdSpec(supervisors.getId())).get(0));
        }
        return supervisorSet;
    }

    private Set<HbnUser> addIfNotExist(List<User> users){
        Set<HbnUser> groupAssociates = new HashSet<>();

        for (User user : users){
            //User does not exist
            if (user.getId() == 0){
                if(user.getFirstName().equals("") || user.getLastName().equals("") || user.getEmail().equals("")) {
                    throw new IllegalArgumentException("Feil i registrering av gruppe: \nFyll ut alle nødvendige felter for bruker!\n(Fornavn, Etternavn og E-Mail)");
                }
                HbnUser hbnUser = new HbnUser(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getTelephone(),
                        user.getEnterpriseID(), (user.getAccessLevel() == null) ? "0" : user.getAccessLevel()
                );

                try {
                    if (user.getTags() != null) hbnUser.setTags(super.getHbnTagSet(user.getTags()));
                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i registrering av gruppe (ny bruker): \nEn eller flere merknader finnes ikke");
                }
                super.addEntity(hbnUser);
                groupAssociates.add(hbnUser);
            }
            //User exists
            else {
                HbnUser hbnUser;
                try {
                    hbnUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(user.getId())).get(0);
                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i registrering av gruppe (eksisterende bruker): \nBruker med id: " + user.getId() + " finnes ikke");
                }

                try {
                    if (user.getTags() != null) hbnUser.setTags(super.getHbnTagSet(user.getTags()));

                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i registrering av gruppe (ny bruker): \nEn eller flere merknader finnes ikke");
                }

                hbnUser.setFirstName(user.getFirstName());
                hbnUser.setLastName(user.getLastName());
                hbnUser.setEmail(user.getEmail());
                hbnUser.setEnterpriseId(user.getEnterpriseID());
                hbnUser.setAccessLevel((user.getAccessLevel() == null) ? "0" : user.getAccessLevel());
                groupAssociates.add(hbnUser);

                super.updateEntity(hbnUser);
            }
        }
        return groupAssociates;
    }

    public String getAssignment(Set<HbnDocument> documents){
        if (documents != null) {
            for (HbnDocument hbnDocument : documents) {
                if (hasAssignment(hbnDocument.getTags())) {
                    return hbnDocument.getTitle();
                }
            }
        }
        return "Ingen Oppgave";
    }

    public boolean hasAssignment(Set<HbnTag> tags){
        for(HbnTag hbnTag : tags) if(hbnTag.getType().toLowerCase().equals("oppgave")) return true;
        return false;
    }

    /*public boolean assignUserToGroup(long userId, long groupId){
        HbnUser groupAssociate = (HbnUser) super.queryToDb(new GetUserByIdSpec(userId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryToDb(new GetGroupByIdSpec(groupId));
        group.getUsers().add(groupAssociate);
        return super.updateEntity(group);
    }
    public boolean assignDocumentToGroup(long documentId, long groupId){
        HbnDocument document = (HbnDocument) super.queryToDb(new GetDocumentByIdSpec(documentId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryToDb(new GetGroupByIdSpec(groupId));
        group.setDocument(document);
        return super.updateEntity(group);
    }*/
}
