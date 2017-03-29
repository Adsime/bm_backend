package com.acc.database;

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
public class DatabaseConenctionTest {

    public static void main(String[] args) {
        UserRepository UR = new UserRepository();
        TagRepository TR = new TagRepository();
        GroupRepository GR = new GroupRepository();
        ProblemRepository PR = new ProblemRepository();
        AccountRepository AR = new AccountRepositoryImpl();

        try {
            String username = UR.getQuery(new GetUserByIdSpec(1)).get(0).getEnterpriseID();
            String password = "password";
            System.out.println("UN: " + username );
            //AR.register(username,password, u1);
        } catch (IllegalArgumentException iae){
            iae.printStackTrace();
            System.out.println(iae.getMessage());
        } catch (EntityNotFoundException enf){
            enf.printStackTrace();
            System.out.println(enf.getMessage());
        }
    }
}