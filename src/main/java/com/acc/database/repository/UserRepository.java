package com.acc.database.repository;

import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.Specification;
import com.acc.models.User;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class UserRepository extends AbstractRepository<HbnUser> implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    public boolean add(User user){

        HbnUser mappedUser = new HbnUser("testfn","HAHAHA","testmail","saltty","duy@accenture");
        return super.addToDb(mappedUser);
    }

    @Override
    public boolean update(User user) {
        return true;
    }

    @Override
    public boolean remove(User user) {
        return false;
    }

    @Override
    public List<User> getQuery(Specification specification) {
        return null;
    }
}
