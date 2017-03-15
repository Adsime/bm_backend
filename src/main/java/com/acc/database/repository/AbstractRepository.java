package com.acc.database.repository;

import com.acc.database.entity.HbnBachelorGroup;
import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnTag;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Tag;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import javax.ejb.NoSuchEntityException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.*;


/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 15.03.2017 Properly implement exceptions
// TODO: 07.03.2017 VERY BLOATED CLASS
public abstract class AbstractRepository{

    private static SessionFactory sessionFactory;

    public AbstractRepository() {
        if (sessionFactory == null) buildSessionFactory();
    }

    public long addEntity(HbnEntity item) {
        long itemId;
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            itemId = (long) session.save(item);
            tx.commit();
        }
        catch (HibernateException he) {
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return 0;
        }
        return itemId;
    }


    public boolean updateEntity(HbnEntity item) throws EntityNotFoundException{
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        }
        //Hibernate actually throws this exception when id is incorrect
        catch (OptimisticLockException ole){
            throw new EntityNotFoundException();
        }
        catch (HibernateException he) {

            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeEntity(HbnEntity item) {

        Transaction tx = null;

        try ( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
        }
        catch(OptimisticLockException ole){
            throw new EntityNotFoundException("THIS SHIT WRONG SON");
        }
        catch (HibernateException he){
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            throw new EntityNotFoundException();
        }
        return true;
    }

    public List<HbnEntity> queryToDb (HqlSpecification spec) throws EntityNotFoundException{
        List<HbnEntity> result = new ArrayList<>();
        Transaction tx = null;
        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            result = session
                    .createQuery(spec.toHqlQuery())
                    .list();
            tx.commit();

            if (result.isEmpty()) throw new EntityNotFoundException();
        }
        catch (OptimisticLockException ole){
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            throw new EntityNotFoundException();
        }
        catch (IndexOutOfBoundsException iobe) {
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            throw new EntityNotFoundException();
        }
        catch (HibernateException he) {
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
            throw new EntityNotFoundException( );
        }
        return result;
    }

    //To be able to do query of different types of objects in the repositories
    public Set<HbnEntity> queryToDb (List<HqlSpecification> specs) throws EntityNotFoundException{
        Set<HbnEntity> result = new HashSet<>();
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            for (HqlSpecification spec : specs){
                result.add((HbnEntity) session
                        .createQuery(spec.toHqlQuery())
                        .list().get(0));
            }
            tx.commit();
        }
        catch (HibernateException he) {

            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        catch (IndexOutOfBoundsException iobe) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    //Finds the corresponding hibernate entity tags with the provided IDs
    public Set<HbnTag> toHbnTagSet (List<Tag> userTags){
        Set<HbnTag> hbnTagSet = new HashSet<>();
        List<HqlSpecification> specList = new ArrayList<>();
        for (Tag tags : userTags) specList.add(new GetTagByIdSpec(tags.getId()));
        Set<HbnEntity> hbnEntitySet = queryToDb(specList);
        for (HbnEntity pojo : hbnEntitySet) hbnTagSet.add((HbnTag)pojo);
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
        if (hbnBachelorGroupSet != null){
            for(HbnBachelorGroup hbnBachelorGroup : hbnBachelorGroupSet){
                groupIdList.add((int) hbnBachelorGroup.getId());
            }
        }
        return groupIdList;
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
            se.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError(se);
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        AbstractRepository.sessionFactory = sessionFactory;
    }
}
