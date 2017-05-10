package com.acc.database;

import com.acc.database.entity.HbnUser;
import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.DocumentRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.*;
import com.acc.database.repository.*;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.models.Document;
import com.acc.models.Group;
import com.acc.models.Tag;
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
        DocumentRepository PR = new DocumentRepository();
        AccountRepository AR = new AccountRepositoryImpl();

        try {
            TR.add(new Tag(0,"En Tag","Rolle","DETTE ER FRA DUY SIN TEST"));
            //PR.add(new Document(0,2,"Cray cray doc","this shit is legit lit", "//some:path",null));
            /*
            User merlin = UR.getQuery(new GetUserByIdSpec(1)).get(0);
            String username = merlin.getEnterpriseID();
            String password = "admin";
            System.out.println("UN: " + username );
            AR.register(username, password,merlin);
            System.out.println("you're logged in fucker");*/
        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        } catch (EntityNotFoundException enf){
            System.out.println(enf.getMessage());
        }
    }
}