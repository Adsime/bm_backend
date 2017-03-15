package com.acc.database.repository;

import com.acc.database.entity.*;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.User;
import com.acc.providers.Links;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

public class UserRepository extends AbstractRepository implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    public User add(User user) throws EntityNotFoundException{
        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "",
                user.getEnterpriseID()
        );

        if (user.getTags() != null) mappedUser.setTags(super.toHbnTagSet(user.getTags()));
        long id = super.addEntity(mappedUser);

        return new User(
                (int) id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnterpriseID(),
                user.getTags()
        );

        // TODO: 09.03.2017 add links yo
    }

    // TODO: 24.02.2017 generate salt 
    @Override
    public boolean update(User user) {
        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "",
                user.getEnterpriseID()
        );

        if (!user.getTags().isEmpty()) mappedUser.setTags(toHbnTagSet(user.getTags()));
        mappedUser.setId(user.getId());
        return super.updateEntity(mappedUser);
    }

    @Override
    public boolean remove(long id) {
        HbnUser mappedUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(id)).get(0);
        if (mappedUser.getProblems() != null){
            for (HbnProblem problem : mappedUser.getProblems()){
                problem.setUser(null);
                super.updateEntity(problem);
            }
        }
        return super.removeEntity(mappedUser);
    }

    @Override
    public List<User> getQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<User> result =  new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnUser hbnUser = (HbnUser)entity;

            User user = new User(
                    (int)hbnUser.getId(),
                    hbnUser.getFirstName(),
                    hbnUser.getLastName(),
                    hbnUser.getEmail(),
                    hbnUser.getEnterpriseId(),
                    hbnUser.getTags() != null ? super.toTagList(hbnUser.getTags()) : new ArrayList<>()
            );

            user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
            user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, toGroupIdList(hbnUser.getGroups())));
            result.add(user);
        }
        return result;
    }
}
