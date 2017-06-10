package com.acc.database;

import com.acc.database.repository.*;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.models.Group;
import com.acc.models.Tag;

import javax.persistence.EntityNotFoundException;

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
            TR.remove(13);
            //PR.add(new Document(0,2,"Cray cray doc","this shit is legit lit", "//some:path",null));
            /*
            User merlin = UR.getQuery(new GetUserByIdSpec(1)).get(0);
            String username = merlin.getEnterpriseID();
            String password = "admin";
            System.out.println("UN: " + username );
            AR.register(username, password,merlin);*/
        } catch (IllegalArgumentException | EntityNotFoundException iae){
            System.out.println(iae.getMessage());
        }
    }
}