package com.acc.database.repository;

import com.acc.database.pojo.HbnProblem;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Problem;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public abstract class AbstractRepository<T>{

    private static SessionFactory sessionFactory;

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

    public List<T> queryFromDb (HqlSpecification spec) {
        List<T> result = new ArrayList<>();

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{

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
        finally {

            session.close();
            return result;
        }

    }

    protected void setUp(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            //sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            MetadataSources ms = new MetadataSources(registry);
            Metadata md = ms.buildMetadata();
            sessionFactory = md.buildSessionFactory();
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

    private static void buildSessionFactory()
    {
        try
        {
            // Create session factory from cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            /*.addAnnotatedClass(User.class) */

            Metadata Meta = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(HbnProblem.class)
                    .addAnnotatedClassName("com.acc.database.pojo.hbnproblem")
                    .getMetadataBuilder()
                    .build();

            SessionFactory sessionFactory = Meta.getSessionFactoryBuilder()
                    .build();

        }
        catch (Throwable ex)
        {
            System.err.println("Initial session factory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
