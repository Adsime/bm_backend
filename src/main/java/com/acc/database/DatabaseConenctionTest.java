package com.acc.database;

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

        DatabaseConenctionTest DCT = new DatabaseConenctionTest();
        DCT.setUp();
    }

    //Set up from Hibernate 5 documentation
    protected void setUp(){
        // A SessionFactory is set up once for an application!
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
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.err.println("Failed to create sessionFactory object: \n" + e + "\n -------------------------------------------------------------- \n");
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
