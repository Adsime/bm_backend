package com.acc.database;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.models.Problem;
import com.acc.database.repository.UserRepository;
import com.acc.models.User;
import org.hibernate.SessionFactory;

import java.util.Arrays;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        //DatabaseConenctionTest DBCT = new DatabaseConenctionTest();
        //DBCT.setUp();
        //Problem problem = new Problem(1,6,"nesten den kuleste oppgaven ever","lag en en kul app bro", "hahahahaah", null);
        UserRepository UR = new UserRepository();
        UR.add(new User());

        //ProblemRepository PR = new ProblemRepository();
        //UR.add(new User()); //Hardkodet User i USERREPO
        //PR.add(problem);
      /*  GetProblemByIdSpec specById = new GetProblemByIdSpec(1);
        GetProblemAllSpec specAll = new GetProblemAllSpec();
        System.out.println("\n" + Arrays.toString(PR.getQuery(specById).toArray()) + "\n");
        System.out.println("\n" + Arrays.toString(PR.getQuery(specAll).toArray()));*/
    }

    /*protected void setUp(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
            try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (org.hibernate.service.spi.ServiceException se) {
            System.err.println("Failed to connect to server");
            se.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError(se);
        }
        catch (Exception e) {
            System.err.println("Failed to create sessionFactory object: \n" + e
                    + "\n -------------------------------------------------------------- \n");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError(e);
        }
    }*/
}
