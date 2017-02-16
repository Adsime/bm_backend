package com.acc.database.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public abstract class AbstractRepository<T>{

    private SessionFactory sessionFactory;

    public AbstractRepository(){
        if (sessionFactory == null) setUp();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public boolean addToDb(T item) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{

            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return false;
        }
        finally {

            session.close();
            return true;
        }
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
