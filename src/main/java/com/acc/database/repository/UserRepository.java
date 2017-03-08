package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.providers.Links;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 01.03.2017 Return User instead of boolean
public class UserRepository extends AbstractRepository<HbnUser> implements Repository<User> {

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
                user.getTags());

        // TODO: 06.03.2017 with Links! 
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
        HbnUser mappedUser = new HbnUser();
        mappedUser.setId(id);
        return super.removeEntity(mappedUser);
    }

    @Override
    public List<User> getQuery(Specification spec) {
        List<HbnUser> readData = super.queryFromDb((HqlSpecification) spec);
        List<User> result = super.toUserList(readData);
        return result;
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
