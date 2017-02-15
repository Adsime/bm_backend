package com.acc.database.repository;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class Repository { //implements IRepository
    public static SessionFactory sessionFactory;

    public Repository(){
        setUp();
    }

    protected void setUp() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {

            System.out.println("creating session factory");
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println("created session factory");

        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();

        }
    }
}
