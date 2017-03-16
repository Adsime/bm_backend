package main.java.com.acc.databaseTest;

import com.acc.database.entity.HbnBachelorGroup;
import com.acc.database.repository.GroupRepository;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.models.Group;
import main.java.com.acc.testResources.TestHibernateData;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by nguyen.duy.j.khac on 15.03.2017.
 */

// TODO: 15.03.2017 Revise
public class GroupRepositoryTest {

    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Query mockQuery;

    private GroupRepository GR;

    @Before
    public void before(){
        initMocks(this);
        GR = Mockito.spy(new GroupRepository());
        GR.setSessionFactory(mockSessionFactory);
    }

    @Test
    public void addGroupWithExistingUsersAndProblem() {

        //Set Up
        Group group = TestHibernateData.getGroup();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnProblemList())
        ;
        when(mockSession.save(any())).thenReturn(1L);

        //Action
        Group actual = GR.add(group);

        //Assert
        Assert.assertTrue(actual.getId()==1);
    }

    @Test
    public void updateGroupSuccess(){

        //Set Up
        Group group = TestHibernateData.getGroup();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnProblemList());


        //Action
        boolean actual = GR.update(group);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateGroupWithWrongId(){

        //Set Up
        Group group = TestHibernateData.getGroupWrongId();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnUserList())
                .thenReturn(TestHibernateData.getHbnProblemList());
        doThrow(new OptimisticLockException()).when(mockSession).update(any());

        //Action
        GR.update(group);
    }

    @Test
    public void removeGroupSuccess(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        //Action
        boolean actual = GR.remove(1);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void removeGroupWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        doThrow(new OptimisticLockException()).when(mockSession).delete(any());

        //Action
        GR.remove(0);
    }

    @Test
    public void getGroupSuccess() {

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnGroupList());

        //Action
        long actual = GR.getQuery(new GetGroupByIdSpec(1)).get(0).getId();
        long expected = 1;

        //Assert
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getGroupWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(new ArrayList());

        //Action
        GR.getQuery(new GetGroupByIdSpec(0));
    }

}
