package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.*;
import com.acc.models.*;
import com.acc.providers.Links;
import org.apache.poi.hssf.util.HSSFColor;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository<HbnBachelorGroup> implements Repository<Group> {

    @Override
    public Group add(Group group) throws EntityNotFoundException{
        Set<HbnUser> groupAssociates = new HashSet<>();

        if (group.getSupervisors() != null) groupAssociates.addAll(toHbnUserSet(group.getSupervisors()));
        if (group.getStudents() != null) groupAssociates.addAll(toHbnUserSet(group.getStudents()));

        HbnBachelorGroup mappedGroup = new HbnBachelorGroup(group.getName());
        if (group.getProblem() != null) mappedGroup.setProblem(toHbnProblem(group.getProblem()));
        mappedGroup.setUsers(groupAssociates);

        long id = super.addEntity(mappedGroup);

        Group addedGroup = new Group(
                (int)id,
                group.getName(),
                group.getStudents(),
                group.getSupervisors(),
                group.getProblem()
        );

        List<Integer> problemId = new ArrayList<>();
        if (group.getProblem() != null )problemId.add(group.getProblem().getId());
        addedGroup.addLinks(Links.PROBLEMS, Links.generateLinks(Links.PROBLEM, problemId));

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

        if (group.getProblem() != null) hbnBachelorGroup.setProblem(toHbnProblem(group.getProblem()));

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
        List<HbnBachelorGroup> readData = super.queryFromDb((HqlSpecification) spec);
        List<Group> result = new ArrayList<>();

        for (HbnBachelorGroup readBachelorGroup : readData ){
            Problem groupProblem;

            if (readBachelorGroup.getProblem() != null){
                groupProblem = new Problem(
                        (int) readBachelorGroup.getProblem().getId(),
                        (int) readBachelorGroup.getProblem().getUser().getId(),
                        "",
                        "",
                        readBachelorGroup.getProblem().getPath(),
                        super.toTagList(readBachelorGroup.getProblem().getTags()
                ));
                List<Integer> authorId = new ArrayList<>(groupProblem.getAuthor());
                groupProblem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            }
            else groupProblem = null;

            Group group = new Group(
                    (int) readBachelorGroup.getId(),
                    readBachelorGroup.getName(),
                    new ArrayList<User>(),
                    new ArrayList<User>(),
                    groupProblem
            );

            for (HbnUser hbnUser : readBachelorGroup.getUsers()){
                User user = new User(
                        (int)hbnUser.getId(),
                        hbnUser.getFirstName(),
                        hbnUser.getLastName(),
                        hbnUser.getEmail(),
                        hbnUser.getEnterpriseId(),
                        super.toTagList(hbnUser.getTags())
                );

                // TODO: 08.03.2017 should add a to groupIdList user ?
                user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
                user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, super.toGroupIdList(hbnUser.getGroups())));

                if (hasStudentTag(hbnUser.getTags())) group.getStudents().add(user);
                else group.getSupervisors().add(user);
            }

            List<Integer> problemId = new ArrayList<>();
            if (group.getProblem() != null )problemId.add(group.getProblem().getId());
            group.addLinks(Links.PROBLEMS, Links.generateLinks(Links.PROBLEM, problemId));

            result.add(group);
        }
        return result;
    }

    private Set<HbnUser> toHbnUserSet(List<User> users){
        Set<HbnUser> result = new HashSet<>();

        List<HqlSpecification> specList = new ArrayList<>();

        for (User user : users){
            specList.add(new GetUserByIdSpec(user.getId()));
        }

        Set<HbnEntity> hbnEntitySet = super.queryByIdSpec(specList);

        for (HbnEntity pojo : hbnEntitySet){
            result.add((HbnUser) pojo);
        }
        return result;
    }

    private HbnProblem toHbnProblem (Problem problem){
        HbnUser hbnUser = (HbnUser) super.queryByIdSpec(new GetUserByIdSpec(problem.getAuthor()));

        HbnProblem hbnProblem = new HbnProblem(
                problem.getPath(),
                hbnUser
        );

        return hbnProblem;
    }

    private boolean hasStudentTag(Set<HbnTag> tags){
        for (HbnTag hbnTag : tags){
            if (hbnTag.getTagName().toLowerCase().equals("student")) return true;
        }
        return false;
    }

    // TODO: 01.03.2017 ILLEGALSTATEEXCEPTION when assigning already assigned user
    public boolean assignUserToGroup(long userId, long groupId){
        HbnUser groupAssociate = (HbnUser) super.queryByIdSpec(new GetUserByIdSpec(userId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryByIdSpec(new GetGroupByIdSpec(groupId));
        group.getUsers().add(groupAssociate);
        return super.updateEntity(group);
    }

    public boolean assignProblemToGroup(long problemId, long groupId){
        HbnProblem problem = (HbnProblem) super.queryByIdSpec(new GetProblemByIdSpec(problemId));
        HbnBachelorGroup group = (HbnBachelorGroup) super.queryByIdSpec(new GetGroupByIdSpec(groupId));
        group.setProblem(problem);
        return super.updateEntity(group);
    }
}
