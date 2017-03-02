package com.acc.database.repository;

import com.acc.database.pojo.HbnEntity;
import com.acc.database.specification.HqlSpecification;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;


import javax.persistence.EntityNotFoundException;
import java.util.*;


/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public abstract class AbstractRepository<T>{

    private static SessionFactory sessionFactory;

    public AbstractRepository(){
        if (sessionFactory == null) buildSessionFactory();
    }

    public long addEntity(T item) throws EntityNotFoundException {
        long itemId;
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            itemId = (long) session.save(item);
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return 0;
        }
        return itemId;
    }

    public boolean updateEntity(T item)  {

        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeEntity(T item) {

        Transaction tx = null;

        try ( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
        }
        // TODO: 24.02.2017 throw custom exception?  
        catch (HibernateException he){
            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return false;
        }
        return true;
    }


    public List<T> queryFromDb (HqlSpecification spec) {

        List<T> result = new ArrayList<>();
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            result = session
                    .createQuery(spec.toHqlQuery())
                    .list();
                tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    //To be able to do query of different types of objects in the repositories
    public Set<HbnEntity> queryByIdSpec (List<HqlSpecification> idSpecs) {

        Set<HbnEntity> result = new HashSet<>();
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            for (HqlSpecification spec : idSpecs){
                result.add( (HbnEntity) session
                        .createQuery(spec.toHqlQuery())
                        .list()
                        .get(0));
            }
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        // TODO: 24.02.2017 Throw a custom exception
        catch (IndexOutOfBoundsException iobe) {
            throw new IllegalArgumentException();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    protected void buildSessionFactory(){
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
