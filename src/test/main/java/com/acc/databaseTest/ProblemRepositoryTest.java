package main.java.com.acc.databaseTest;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.models.Problem;
import main.java.com.acc.testResources.TestHibernateData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
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
 * Created by nguyen.duy.j.khac on 15.03.2017.
 */
public class ProblemRepositoryTest {
    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Query mockQuery;

    private ProblemRepository PR;

    @Before
    public void before(){
        initMocks(this);
        PR = Mockito.spy(new ProblemRepository());
        PR.setSessionFactory(mockSessionFactory);
    }

    @Test
    public void addProblemSuccess() {

        //Set Up
        Problem problem = TestHibernateData.getProblem();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());
        when(mockSession.save(any())).thenReturn(1L);

        //Action
        Problem actual = PR.add(problem);

        //Assert
        Assert.assertTrue(actual.getId()==1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addProblemWithNonExistingUser() {

        //Set Up
        Problem problem = TestHibernateData.getProblemNoAuthor();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        doThrow(new OptimisticLockException()).when(mockQuery).list();

        //Action
        PR.add(problem);
    }

    @Test
    public void updateProblemSuccess(){

        //Set Up
        Problem problem = TestHibernateData.getProblem();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());

        //Action
        boolean actual = PR.update(problem);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateProblemWithWrongId(){

        //Set Up
        Problem problem = TestHibernateData.getProblemWrongId();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());
        doThrow(new OptimisticLockException()).when(mockSession).update(any());

        //Action
        PR.update(problem);
    }

    @Test
    public void removeProblemSuccess(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        //Action
        boolean actual = PR.remove(1);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void removeProblemWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        doThrow(new OptimisticLockException()).when(mockSession).delete(any());

        //Action
        PR.remove(0);
    }

    @Test
    public void getProblemSuccess() {

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnProblemList());

        //Action
        long actual = PR.getQuery(new GetProblemByIdSpec(1)).get(0).getId();
        long expected = 1;

        //Assert
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getProblemWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnProblemList());

        //Action
        PR.getQuery(new GetProblemByIdSpec(0));
    }
}
