package com.acc.database.repository;

import com.acc.database.pojo.HbnBachelorGroup;
import com.acc.database.pojo.HbnEntity;
import com.acc.database.pojo.HbnTag;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.providers.Links;
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

// TODO: 07.03.2017 VERY BLOATED CLASS
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
            throw new EntityNotFoundException();
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
    public Set<HbnEntity> queryByIdSpec (List<HqlSpecification> idSpecs) throws EntityNotFoundException{

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
            throw new EntityNotFoundException();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public HbnEntity queryByIdSpec (HqlSpecification idSpec) {

        HbnEntity result = null;
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();

            result = (HbnEntity) session
                    .createQuery(idSpec.toHqlQuery())
                    .list()
                    .get(0);

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

    public Set<HbnTag> toHbnTagSet (List<Tag> userTags){
        Set<HbnTag> hbnTagSet = new HashSet<>();

        List<HqlSpecification> specList = new ArrayList<>();

        for (Tag tags : userTags){
            specList.add(new GetTagByIdSpec(tags.getId()));
        }

        Set<HbnEntity> hbnEntitySet = queryByIdSpec(specList);

        for (HbnEntity pojo : hbnEntitySet){
            hbnTagSet.add((HbnTag)pojo);
        }

        return hbnTagSet;
    }

    public List<Tag> toTagList(Set<HbnTag> tagSet){
        List<Tag> tagList = new ArrayList<>();

        for (HbnTag hbnTag : tagSet){
            tagList.add(new Tag(
                    (int) hbnTag.getId(),
                    hbnTag.getTagName(),
                    hbnTag.getType(),
                    hbnTag.getDescription()
            ));
        }

        return tagList;
    }

    public List<Integer> toGroupIdList(Set<HbnBachelorGroup> hbnBachelorGroupSet){
        List<Integer> groupIdList = new ArrayList<>();

        for(HbnBachelorGroup hbnBachelorGroup : hbnBachelorGroupSet){
            groupIdList.add((int) hbnBachelorGroup.getId());
        }

        return groupIdList;
    }

    public List<User> toUserList (List<HbnUser> hbnUserList){
        List <User> userList = new ArrayList<>();

        for (HbnUser hbnUser : hbnUserList){
            User user = new User(
                    (int)hbnUser.getId(),
                    hbnUser.getFirstName(),
                    hbnUser.getLastName(),
                    hbnUser.getEmail(),
                    hbnUser.getEnterpriseId(),
                    toTagList(hbnUser.getTags())
            );

            user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
            user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, toGroupIdList(hbnUser.getGroups())));
            userList.add(user);
        }

        return userList;
    }

    private void buildSessionFactory(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry )
                    .buildMetadata()
                    .buildSessionFactory();
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
