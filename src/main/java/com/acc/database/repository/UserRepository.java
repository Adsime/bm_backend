package com.acc.database.repository;

import com.acc.database.pojo.HbnGroup;
import com.acc.database.pojo.HbnProblem;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Group;
import com.acc.models.User;

import java.util.ArrayList;
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

        HbnUser mappedUser = new HbnUser(user.getFirstName(), user.getLastName(), user.getEmail(),"",user.getEnterpriseID());
        return super.addEntity(mappedUser);
    }

    @Override
    public boolean update(User user) {
        HbnUser mappedUser = new HbnUser(user.getFirstName(), user.getLastName(), user.getEmail(),"",user.getEnterpriseID());
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
                    null,
                    null
            ));
        }

        return result;
    }

    private List<Group> getGroupList(List<HbnGroup> hbnList){
        List<Group> groupList = new ArrayList<>();

        for(HbnGroup hbnGroup : hbnList){
            groupList.add(
                    new Group((int)hbnGroup.getId(),
                            hbnGroup.getName(),
                            "",
                            null));
        }
    }
}
