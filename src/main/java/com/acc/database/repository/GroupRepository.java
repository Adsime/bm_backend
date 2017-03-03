package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
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
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository<HbnBachelorGroup> implements Repository<Group> {

    @Override
    public Group add(Group group) {
        Set<HbnUser> hbnUsers = toHbnUserSet(group.getUsers());

        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup(group.getName());
        hbnBachelorGroup.setUsers(hbnUsers);
        long id = super.addEntity(hbnBachelorGroup);

        return new Group(
                (int)id,
                group.getName(),
                group.getUsers()
        );
    }

    @Override
    public boolean update(Group group) {
        // TODO: 01.03.2017 Map object
        // TODO: 01.03.2017 Call super updateEntity method
        return false;
    }

    @Override
    public boolean remove(long id) {
        // TODO: 01.03.2017 Map object
        // TODO: 01.03.2017 Call super updateEntity method
        return false;
    }

    @Override
    public List<Group> getQuery(Specification specification) {
        return null;
    }

    // TODO: 01.03.2017 toHbnUserSet
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

    // TODO: 01.03.2017 AssignToGroup
}
