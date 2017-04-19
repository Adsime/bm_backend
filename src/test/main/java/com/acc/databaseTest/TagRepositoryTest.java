package main.java.com.acc.databaseTest;

import com.acc.database.repository.TagRepository;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.models.Tag;
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
 * Created by nguyen.duy.j.khac on 15.03.2017.
 */
@Ignore
public class TagRepositoryTest {
    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Query mockQuery;

    private TagRepository TR;

    @Before
    public void before(){
        initMocks(this);
        TR = Mockito.spy(new TagRepository());
        TR.setSessionFactory(mockSessionFactory);
    }

    @Test
    public void addTagSuccess() {

        //Set Up
        Tag tag = TestHibernateData.getTag();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.save(any())).thenReturn(1L);

        //Action
        Tag actual = TR.add(tag);

        //Assert
        Assert.assertTrue(actual.getId()==1);
    }

    @Test
    public void updateTagSuccess(){

        //Set Up
        Tag tag = TestHibernateData.getTag();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        //Action
        boolean actual = TR.update(tag);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateTagWithWrongId(){

        //Set Up
        Tag tag = TestHibernateData.getTagWrongId();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        doThrow(new OptimisticLockException()).when(mockSession).update(any());

        //Action
        TR.update(tag);
    }

    @Test
    public void removeTagSuccess(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        //Action
        boolean actual = TR.remove(1);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void removeTagWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        doThrow(new OptimisticLockException()).when(mockSession).delete(any());

        //Action
        TR.remove(0);
    }

    @Test
    public void getTagSuccess() {

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnTagList());

        //Action
        long actual = TR.getQuery(new GetTagByIdSpec(1)).get(0).getId();
        long expected = 1;

        //Assert
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getTagWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnTagList());

        //Action
        TR.getQuery(new GetTagByIdSpec(0));
    }
}
