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
    public Group add(Group group) throws EntityNotFoundException{
        Set<HbnUser> groupAssociates = new HashSet<>();
        //addIfNotExist() throws EntityNotFoundException
        if (group.getSupervisors() != null) groupAssociates.addAll(addIfNotExist(group.getSupervisors()));
        if (group.getStudents() != null) groupAssociates.addAll(addIfNotExist(group.getStudents()));

        HbnBachelorGroup mappedGroup = new HbnBachelorGroup(group.getName());

        try {
            if (group.getTags() != null) mappedGroup.setTags(super.getHbnTagSet(group.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av gruppe: \nEn eller flere merknader finnes ikke");
        }

        try {
            if (group.getProblem() != null) mappedGroup.setProblem(getHbnProblem(group.getProblem()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av gruppe: \nOppgave med id: " + group.getProblem().getId() + " finnes ikke");
        }
        mappedGroup.setUsers(groupAssociates);

        long id = super.addEntity(mappedGroup);

               Group addedGroup = new Group(
                (int)id,
                group.getName(),
                group.getStudents(),
                group.getSupervisors(),
                group.getProblem()
        );
        return addedGroup;
    }

    @Override
    public boolean update(Group group) {
        List<User> groupAssociates = new ArrayList<>();
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup(group.getName());
        hbnBachelorGroup.setId(group.getId());

        if (group.getSupervisors() != null) groupAssociates.addAll(group.getSupervisors());
        if (group.getStudents() != null) groupAssociates.addAll(group.getStudents());
        hbnBachelorGroup.setUsers(toHbnUserSet(groupAssociates));
        if (group.getTags() != null) hbnBachelorGroup.setTags(super.getHbnTagSet(group.getTags()));
        if (group.getProblem() != null) hbnBachelorGroup.setProblem(getHbnProblem(group.getProblem()));

        return super.updateEntity(hbnBachelorGroup);
    }

    @Override
    public boolean remove(long id) {
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup();
        hbnBachelorGroup.setId(id);
        return super.removeEntity(hbnBachelorGroup);
    }

    @Override
    public List<Group> getQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<Group> result = new ArrayList<>();

        for (HbnEntity entity : readData ){
            Problem groupProblem;
            HbnBachelorGroup hbnBachelorGroup = (HbnBachelorGroup) entity;

            if (hbnBachelorGroup.getProblem() != null){
                groupProblem = new Problem(
                        (int) hbnBachelorGroup.getProblem().getId(),
                        (int) hbnBachelorGroup.getProblem().getUser().getId(),
                        "",
                        "",
                        hbnBachelorGroup.getProblem().getPath(),
                        super.toTagList(hbnBachelorGroup.getProblem().getTags()
                ));
                List<Integer> authorId = new ArrayList<>(groupProblem.getAuthor());
                groupProblem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            }
            else groupProblem = null;

            Group group = new Group(
                    (int) hbnBachelorGroup.getId(),
                    hbnBachelorGroup.getName(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    groupProblem
            );

            for (HbnUser hbnUser : hbnBachelorGroup.getUsers()){
                User user = new User(
                        (int)hbnUser.getId(),
                        hbnUser.getFirstName(),
                        hbnUser.getLastName(),
                        hbnUser.getEmail(),
                        hbnUser.getEnterpriseId(),
                        hbnUser.getAccessLevel(),
                        super.toTagList(hbnUser.getTags())
                );

                if (hasStudentTag(hbnUser.getTags())) group.getStudents().add(user);
                else group.getSupervisors().add(user);
            }

            List<Integer> userIdList = new ArrayList();
            for (HbnUser hbnUser : hbnBachelorGroup.getUsers()) userIdList.add((int)hbnUser.getId());
            group.addLinks(Links.USERS, Links.generateLinks(Links.USER, userIdList));

            if (group.getProblem() != null ) {
                List<Integer> problemId = new ArrayList<>();
                problemId.add(group.getProblem().getId());
                group.addLinks(Links.PROBLEMS, Links.generateLinks(Links.PROBLEM, problemId));
            }

            if (hbnBachelorGroup.getTags() != null) {
                group.setTags(super.toTagList(hbnBachelorGroup.getTags()));
                group.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, group.getTagIdList()));
            }

            result.add(group);
        }
        return result;
    }

    @Override
    public List<Group> getMinimalQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<Group> result = new ArrayList<>();

        for (HbnEntity entity : readData ){
            Problem groupProblem;
            HbnBachelorGroup hbnBachelorGroup = (HbnBachelorGroup) entity;
            HbnProblem hbnProblem = hbnBachelorGroup.getProblem();

            if (hbnProblem != null){
                groupProblem = new Problem();
                groupProblem.setId((int)hbnProblem.getId());
                groupProblem.setTitle(hbnProblem.getTitle());
            }

            Group group = new Group();
            group.setId((int)hbnBachelorGroup.getId());
            group.setName(hbnBachelorGroup.getName());
            group.setTags(super.toTagList(hbnBachelorGroup.getTags()));


            List<Integer> userIdList = new ArrayList();
            for (HbnUser hbnUser : hbnBachelorGroup.getUsers()) userIdList.add((int)hbnUser.getId());
            group.addLinks(Links.USERS, Links.generateLinks(Links.USER, userIdList));

            if (hbnProblem != null ) {
                List<Integer> problemId = new ArrayList<>();
                problemId.add(group.getProblem().getId());
                group.addLinks(Links.PROBLEMS, Links.generateLinks(Links.PROBLEM, problemId));
            }

            if (hbnBachelorGroup.getTags() != null) {
                group.setTags(super.toTagList(hbnBachelorGroup.getTags()));
                group.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, group.getTagIdList()));
            }

            result.add(group);
        }
        return result;
    }

    private Set<HbnUser> toHbnUserSet(List<User> users){
        Set<HbnUser> result = new HashSet<>();
        List<HqlSpecification> specList = new ArrayList<>();
        for (User user : users) specList.add(new GetUserByIdSpec(user.getId()));
        Set<HbnEntity> hbnEntitySet = super.queryToDb(specList);
        for (HbnEntity entity : hbnEntitySet) result.add((HbnUser) entity);
        return result;
    }

    private HbnProblem getHbnProblem(Problem problem){
        return (HbnProblem) super.queryToDb(new GetProblemByIdSpec(problem.getId())).get(0);
    }

    private boolean hasStudentTag(Set<HbnTag> tags){
        for (HbnTag hbnTag : tags){
            if (hbnTag.getTagName().toLowerCase().equals("student")) return true;
        }
        return false;
    }

    public boolean assignUserToGroup(long userId, long groupId){
        HbnUser groupAssociate = (HbnUser) super.queryToDb(new GetUserByIdSpec(userId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryToDb(new GetGroupByIdSpec(groupId));
        group.getUsers().add(groupAssociate);
        return super.updateEntity(group);
    }

    public boolean assignProblemToGroup(long problemId, long groupId){
        HbnProblem problem = (HbnProblem) super.queryToDb(new GetProblemByIdSpec(problemId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryToDb(new GetGroupByIdSpec(groupId));
        group.setProblem(problem);
        return super.updateEntity(group);
    }

    private Set<HbnUser> addIfNotExist(List<User> users){
        Set<HbnUser> groupAssociates = new HashSet<>();

        for (User user : users){
            if (user.getId() == 0){
                HbnUser hbnUser = new HbnUser(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        "",
                        user.getEnterpriseID(),
                        (user.getAccessLevel() == null) ? "0" : user.getAccessLevel()
                );

                try {
                    if (user.getTags() != null) hbnUser.setTags(super.getHbnTagSet(user.getTags()));
                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i registrering av gruppe (ny bruker): \nEn eller flere merknader finnes ikke");
                }
                super.addEntity(hbnUser);
                groupAssociates.add(hbnUser);
            }
            else {
                HbnUser hbnUser;
                try {
                    hbnUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(user.getId())).get(0);
                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i registrering av gruppe (eksisterende bruker): \nBruker med id: " + user.getId() + " finnes ikke");
                }
                groupAssociates.add(hbnUser);
            }
        }
        return groupAssociates;
    }
}
