package com.acc.database;

import com.acc.database.entity.HbnUser;
import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.ProblemRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.repository.*;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.database.specification.GetUserByTagSpec;
import com.acc.models.Group;
import com.acc.models.User;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConnectionTest {

    public static void main(String[] args) {
        UserRepository UR = new UserRepository();
        TagRepository TR = new TagRepository();
        GroupRepository GR = new GroupRepository();
        ProblemRepository PR = new ProblemRepository();
        AccountRepository AR = new AccountRepositoryImpl();

        try {
            User merlin = UR.getQuery(new GetUserByIdSpec(8)).get(0);
            merlin.setEnterpriseID("YOLO");
            String username = merlin.getEnterpriseID();
            String password = "passsword";
            System.out.println("UN: " + username );
            User user = AR.register(username, password,merlin);
            System.out.println("you're logged in fucker");

        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        } catch (EntityNotFoundException enf){
            System.out.println(enf.getMessage());
        }
    }
}