package com.acc.database.repository;

import com.acc.database.pojo.HbnUser;
import com.acc.models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class UserRepository implements IRepository<User>{

    SessionFactory sessionFactory;

    public UserRepository( SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean add(User user){

        HbnUser mappedUser = new HbnUser("testfn","HAHAHA","testmail","saltty");
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{

            tx = session.beginTransaction();
            session.save(mappedUser);
            tx.commit();
        }
        catch (HibernateException e) {

            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        }
        finally {

            session.close();
            return true;
        }
    }

    @Override
    public boolean update(User user) {
        return true;
    }

    @Override
    public boolean remove(User user) {
        return false;
    }
}
