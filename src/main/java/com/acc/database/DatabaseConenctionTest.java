package com.acc.database;

import com.acc.database.pojo.Tag;
import com.acc.database.pojo.User;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.xml.rpc.ServiceException;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        //TagRepository TR = new TagRepository();
        UserRepository UR = new UserRepository();
        UR.add(new User("Hohkun","Butterbucht","hb@assenture","SALT"));
       // TR.add(new Tag("Dickface", "People that totalt Dickfaces"));
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


    /*public Integer addPerson(String name, String talent){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer personID = null;
        try{
            tx = session.beginTransaction();
            Person person = new Person(name, talent);
            personID = (Integer) session.save(person);
            tx.commit();
        }catch (HibernateException e)
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return personID;
    }*/
}
