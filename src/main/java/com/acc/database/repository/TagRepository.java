package com.acc.database.repository;

import com.acc.database.pojo.HbnTag;
import com.acc.database.specification.Specification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 14.02.2017.
 */
public class TagRepository implements IRepository<HbnTag>{

    public TagRepository(){
        super();
    }

    public boolean add(HbnTag newHbnTag){

        /*Session session = sessionFactory.openSession();
        Transaction trans = null;
        Integer tagID = null;

        try{

            trans = session.beginTransaction();
            tagID = (Integer) session.save(newHbnTag);
            trans.commit();
        }
        catch (HibernateException e) {

            if (trans!=null) trans.rollback();
            e.printStackTrace();
            return false;
        }
        finally {

            session.close();
            return true;
        }*/return false;
    }

    @Override
    public boolean update(HbnTag item) {
        return false;
    }

    @Override
    public boolean remove(HbnTag item) {
        return false;
    }

    @Override
    public List<HbnTag> query(Specification specification) {
        return null;
    }

    /*public boolean listEmployees( ){

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM HbnTag").list();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext();){
                HbnTag tag = (HbnTag) iterator.next();
                System.out.println("Name: " + tag.getTagName());
                System.out.println("Desc: " + tag.getDescription());
            }
            tx.commit();
        }catch (HibernateException e) {

            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;

        }finally {

            session.close();
            return true;
        }
    }*/
}
