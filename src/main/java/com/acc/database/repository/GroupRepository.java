package com.acc.database.repository;

import com.acc.database.pojo.HbnGroup;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.Specification;
import com.acc.models.Group;
import com.acc.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository<HbnGroup> implements Repository<Group> {

    @Override
    public Group add(Group group) {
        // TODO: 01.03.2017 Validate users
        // TODO: 01.03.2017 Map object
        // TODO: 01.03.2017 Call super addEntity method
        return null;
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

    // TODO: 01.03.2017 getUserSet
    private Set<HbnUser> getUserSet(List<User> users){
        return new HashSet<>();
    }
    // TODO: 01.03.2017 AssignToGroup
}
