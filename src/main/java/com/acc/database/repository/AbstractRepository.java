package com.acc.database.repository;

import com.acc.database.entity.HbnBachelorGroup;
import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnTag;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Tag;
import com.acc.models.User;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.*;


/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 * Includes hibernate methods for CRUD, and utility methods
 */
@SuppressWarnings("all")
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

    /**
     * Updates an entity within a sessions's transaction scope with Hibernate
     *
     * @param item HbnEntity
     * @return boolean
     */
    public boolean updateEntity(HbnEntity item) {
        Transaction tx = null;

        try( Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        }
        catch (OptimisticLockException ole){
            throw new EntityNotFoundException();

        } catch (HibernateException he) {
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
            throw new EntityNotFoundException();
        }
        catch (HibernateException he){
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        return true;
    }

    public List<HbnEntity> queryToDb (HqlSpecification spec) {
        List result = new ArrayList<>();
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
        catch (HibernateException he) {
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        return result;
    }

    //To be able to do query of different types of objects in the repositories
    public Set<HbnEntity> queryToDb (List<HqlSpecification> specs){
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

        } catch (OptimisticLockException|IndexOutOfBoundsException ex){
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            throw new EntityNotFoundException();
        } catch (HibernateException he) {
            if (tx != null && tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();
        }
        return result;
    }

    //Finds the corresponding hibernate entity tags with the provided IDs
    public Set<HbnTag> getHbnTagSet(List<Tag> userTags){
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

    public HbnUser toHbnUser(User user){
        return new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTelephone(),
                user.getEnterpriseID(),
                user.getAccessLevel() == null ? "0" : user.getAccessLevel()
        );
    }

    public User toUser(HbnUser hbnUser){
        return new User(
                (int)hbnUser.getId(),
                hbnUser.getFirstName(),
                hbnUser.getLastName(),
                hbnUser.getEmail(),
                hbnUser.getTelephone(),
                hbnUser.getEnterpriseId(),
                hbnUser.getAccessLevel(),
                hbnUser.getTags() != null ? toTagList(hbnUser.getTags()) : new ArrayList<>()
        );
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
