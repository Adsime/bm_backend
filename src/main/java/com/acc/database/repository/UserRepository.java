package com.acc.database.repository;

import com.acc.models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class UserRepository extends Repository implements IRepository<User>{
    public UserRepository(){
        super();
    }

    @Override
    public boolean add(User user){

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{

            tx = session.beginTransaction();
            session.save(user);
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
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{

          /*  tx = session.beginTransaction();
            User updateTarget = (User)session.load(User.class, user.getId());
            updateTarget.setFirstName();
            updateTarget.setLastName();
            updateTarget.setEmail();
            updateTarget.setGroups();
            updateTarget.setTags();
            tx.commit();*/
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
    public boolean remove(User user) {
        return false;
    }
}
