package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Group;
import com.acc.models.Tag;
import com.acc.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class UserRepository extends AbstractRepository<HbnUser> implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    public boolean add(User user){

        HbnUser mappedUser = new HbnUser(user.getFirstName(), user.getLastName(), user.getEmail(),"",user.getEnterpriseID());
        if (!user.getTags().isEmpty()) mappedUser.setTags(getHbnUserTags(user));
        if (!user.getTags().isEmpty()) mappedUser.setGroups(getHbnGroupsByIds());
        return super.addEntity(mappedUser);
    }

    @Override
    public boolean update(User user) {
        HbnUser mappedUser = new HbnUser(user.getFirstName(), user.getLastName(), user.getEmail(),"",user.getEnterpriseID());
        if (!user.getTags().isEmpty()) mappedUser.setTags(getHbnUserTags(user));
        mappedUser.setId(user.getId());
        return super.updateEntity(mappedUser);
    }

    @Override
    public boolean remove(long id) {
        HbnUser mappedUser = new HbnUser();
        mappedUser.setId(id);
        return super.removeEntity(mappedUser);
    }

    @Override
    public List<User> getQuery(Specification spec) {
        List<HbnUser> readData = super.queryFromDb((HqlSpecification) spec);
        List<User> result = new ArrayList<>();

        for (HbnUser readUser : readData){
            result.add( new User(
                    readUser.getId(),
                    readUser.getFirstName(),
                    readUser.getLastName(),
                    readUser.getEmail(),
                    readUser.getEnterpriseId(),
                    getGroupIds(readUser.getGroups()),
                    getUserTags(readUser)
            ));
        }

        return result;
    }

    private List<Integer> getGroupIds(Set<HbnGroup> hbnGroupList){
        List<Integer> idList = new ArrayList<>();

        for(HbnGroup hbnGroup : hbnGroupList){
            idList.add((int)hbnGroup.getId());
        }

        return idList;
    }

    private Set<HbnGroup> getHbnGroupsByIds(List<Integer> groupIds){
        List<HqlSpecification> idSpecList = new ArrayList<>();

        for (Integer id : groupIds) {
            idSpecList.add(new GetGroupByIdSpec(id));
        }

        Set<HbnPOJO> readData = super.queryByIdSpec(idSpecList);
        Set<HbnGroup> result = new HashSet<>();

        for (HbnPOJO readPOJO : readData){
            result.add((HbnGroup)readPOJO);
        }

        return result;
    }

    private List<Tag> getUserTags (HbnUser hbnUser){
        List<Tag> tagList = new ArrayList<>();

        for (HbnTag hbnTag : hbnUser.getTags()){
            tagList.add(new Tag(
                    (int)hbnTag.getId(),
                    hbnTag.getTagName(),
                    hbnTag.getType(),
                    hbnTag.getDescription()
            ));
        }

        return tagList;
    }

    private Set<HbnTag> getHbnUserTags (User user){
        Set<HbnTag> tagSet = new HashSet<>();

        for (Tag tag : user.getTags()){
            tagSet.add(new HbnTag(
                    tag.getName(),
                    tag.getDescription(),
                    tag.getType()
            ));
        }

        return tagSet;
    }
}
