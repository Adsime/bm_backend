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

        //TagRepository TR = new TagRepository();
       // TR.add(new HbnTag("Dickface", "People that totalt Dickfaces"));
        DatabaseConenctionTest DBCT = new DatabaseConenctionTest();
        DBCT.setUp();
        UserRepository UR = new UserRepository(sessionFactory);
        UR.add(new User());
        ProblemRepository PR = new ProblemRepository(sessionFactory);
        PR.add
    }

    protected void setUp(){
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
    }
}
