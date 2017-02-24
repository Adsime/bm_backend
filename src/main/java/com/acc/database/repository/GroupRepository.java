package com.acc.database.repository;

import com.acc.database.pojo.HbnGroup;
import com.acc.database.specification.Specification;
import com.acc.models.Group;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 24.02.2017.
 */
public class GroupRepository extends AbstractRepository<HbnGroup> implements Repository<Group> {

    @Override
    public boolean add(Group item) {
        return false;
    }

    @Override
    public boolean update(Group item) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public List<Group> getQuery(Specification specification) {
        return null;
    }
}
