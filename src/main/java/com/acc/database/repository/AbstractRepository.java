package com.acc.database.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public abstract class AbstractRepository<T>{

    private SessionFactory sessionFactory;

    public AbstractRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public boolean add(T item) {
        return this.adds(item);
    }
    public boolean adds(T item) {

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

    public boolean update(T item) {
        return false;
    }

    public boolean remove(T item) {
        return false;
    }
}
