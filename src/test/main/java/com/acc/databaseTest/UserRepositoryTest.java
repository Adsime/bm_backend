package main.java.com.acc.databaseTest;

import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.User;
import main.java.com.acc.testResources.TestHibernateData;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;



/**
 * Created by nguyen.duy.j.khac on 10.03.2017.
 */
@Ignore
public class UserRepositoryTest {
    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Query mockQuery;

    private UserRepository UR;

    @Before
    public void before(){
        initMocks(this);
        UR = Mockito.spy(new UserRepository());
        UR.setSessionFactory(mockSessionFactory);

    }

    @Test
    public void addUserSuccess() {

        //Set Up
        User user = TestHibernateData.getUser();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.save(any())).thenReturn(1L);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnTagList());

        //Action
        User actual = UR.add(user);

        //Assert
        Assert.assertTrue(actual.getId()==1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addUserWithNonExistingTags() {

        //Set Up
        User user = TestHibernateData.getUser();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.save(any())).thenReturn(1L);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnTagList());

        //Action
        UR.add(user);
    }

    /*@Test
    public void updateUserSuccess(){

        //Set Up
        User testUser = TestHibernateData.getContextUser();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnTagList());

        //Action
        boolean actual = UR.update(testUser);

        //Assert
        Assert.assertTrue(actual);
    }*/

    @Test(expected = EntityNotFoundException.class)
    public void updateUserWithNonExistingTags(){

        //Set Up
        User user = TestHibernateData.getUser();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnTagList());

        //Action
        UR.update(user);
    }

    /*@Test(expected = EntityNotFoundException.class)
    public void updateUserWithWrongId(){

        //Set Up
        User user = TestHibernateData.getUserWrongId();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnTagList());
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        doThrow(new OptimisticLockException()).when(mockSession).update(any());

        //Action
        UR.update(user);
    }*/

    @Test
    public void removeUserSuccess(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList());

        //Action
        boolean actual = UR.remove(1);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void removeUserWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnUserList());

        //Action
        UR.remove(0);
    }

    @Test
    public void getUserSuccess() {

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList());

        //Action
        long actual = UR.getQuery(new GetUserByIdSpec(1)).get(0).getId();
        long expected = 1;

        //Assert
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getUserWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnUserList());

        //Action
        UR.getQuery(new GetUserByIdSpec(0));
    }

}
