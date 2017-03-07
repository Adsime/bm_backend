package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.*;
import com.acc.providers.Links;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository<HbnBachelorGroup> implements Repository<Group> {

    @Override
    public Group add(Group group) {
        // TODO: 07.03.2017 check supervisor
        // TODO: 07.03.2017 add users except supervisors
        HbnBachelorGroup mappedGroup = new HbnBachelorGroup(group.getName());

        boolean noUsers = group.getUsers() != null || !(group.getUsers().isEmpty());
        boolean noProblem = group.getProblem() != null;

        if (noUsers) mappedGroup.setUsers(toHbnUserSet(group.getUsers()));
        if (noProblem) mappedGroup.setHbnProblem(toHbnProblem(group.getProblem()));

        long id = super.addEntity(mappedGroup);

        Group addedGroup = new Group(
                (int)id,
                group.getName(),
                group.getUsers()
        );

        // TODO: 06.03.2017 generate links with one id
        //addedGroup.addLinks(Links.PROBLEMS, Links.generateLinks(Links.PROBLEM, group.getProblem().getId()));

        return addedGroup;
    }

    @Override
    public boolean update(Group group) {
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup(group.getName());
        hbnBachelorGroup.setId(group.getId());

        if (group.getUsers() != null) hbnBachelorGroup.setUsers(toHbnUserSet(group.getUsers()));
        if (group.getProblem() != null) hbnBachelorGroup.setHbnProblem(toHbnProblem(group.getProblem()));

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
            Group group = new Group(
                    (int) readBachelorGroup.getId(),
                    readBachelorGroup.getName(),
                    toUserList(readBachelorGroup.getUsers())
            );
            // TODO: 07.03.2017 add problem link! 
            result.add(group);
        }
        return result;
    }

    private Set<HbnUser> toHbnUserSet(List<User> users){
        Set<HbnUser> hbnUserSet = new HashSet<>();

        List<HqlSpecification> specList = new ArrayList<>();

        for (User user : users){
            specList.add(new GetUserByIdSpec(user.getId()));
        }

        Set<HbnEntity> hbnEntitySet = super.queryByIdSpec(specList);

        for (HbnEntity pojo : hbnEntitySet){
            hbnUserSet.add((HbnUser) pojo);
        }
        return hbnUserSet;
    }

    private List<User> toUserList (Set<HbnUser> hbnUserSet){
        List <User> userList = new ArrayList<>();

        for (HbnUser hbnUser : hbnUserSet){
            User user = new User(
                    (int)hbnUser.getId(),
                    hbnUser.getFirstName(),
                    hbnUser.getLastName(),
                    hbnUser.getEmail(),
                    hbnUser.getEnterpriseId(),
                    super.toTagList(hbnUser.getTags())
            );

            user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
            user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, super.toGroupIdList(hbnUser.getGroups())));
            userList.add(user);
        }

        return userList;
    }

    private HbnProblem toHbnProblem (Problem problem){
        HbnUser hbnUser = (HbnUser) super.queryByIdSpec(new GetUserByIdSpec(problem.getAuthor()));

        HbnProblem hbnProblem = new HbnProblem(
                problem.getPath(),
                hbnUser
        );

        return hbnProblem;
    }


    // TODO: 01.03.2017 AssignUserToGroup
    public boolean assignUserToGroup(User user){
        // TODO: 07.03.2017 map user to hbnuser
        // TODO: 07.03.2017 update group
        return false;
    }

    // TODO: 03.03.2017 AssignProblemToGroup
}
