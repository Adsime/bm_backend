package com.acc.database.repository;

import com.acc.database.pojo.HbnProblem;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Problem;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.transaction.Status;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public abstract class AbstractRepository<T>{

    private SessionFactory sessionFactory;

    public AbstractRepository(){
        if (sessionFactory == null) buildSessionFactory();
    }

    public boolean addEntity(T item) {

        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return false;
        }
        return true;
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

        try( Session session = sessionFactory.openSession();){

            tx = session.beginTransaction();
            TypedQuery<T> query = session.createQuery(spec.toHqlQuery());
            result = query.getResultList();
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

    protected void buildSessionFactory(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();

            /*MetadataSources ms = new MetadataSources(registry);
            Metadata md = ms.buildMetadata();
            sessionFactory = md.buildSessionFactory();*/
            /*Configuration c = new Configuration();
            c = c.configure();
            sessionFactory = c.buildSessionFactory();*/

            System.out.println("her");

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
