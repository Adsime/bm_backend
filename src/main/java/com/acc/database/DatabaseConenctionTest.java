package com.acc.database;

import com.acc.database.pojo.HbnUser;
import com.acc.database.repository.ProblemRepository;
import com.acc.models.Problem;
import com.acc.models.User;
import com.acc.database.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        //DatabaseConenctionTest DBCT = new DatabaseConenctionTest();
        //DBCT.setUp();
        Problem problem = new Problem(1,1,"nesten den kuleste oppgaven ever","lag en en kul app bro", "c://kulapp.exe", null);
        ProblemRepository PR = new ProblemRepository();
        PR.add(problem);
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
