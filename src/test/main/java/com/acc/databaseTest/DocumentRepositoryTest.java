package main.java.com.acc.databaseTest;

import com.acc.database.repository.DocumentRepository;
import com.acc.database.specification.GetDocumentByIdSpec;
import com.acc.models.Document;
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
public class DocumentRepositoryTest {
    @Mock
    private Session mockSession;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Query mockQuery;

    private DocumentRepository DR;

    @Before
    public void before(){
        initMocks(this);
        DR = Mockito.spy(new DocumentRepository());
        DR.setSessionFactory(mockSessionFactory);
    }

    @Test
    public void addDocumentSuccess() {

        //Set Up
        Document document = TestHibernateData.getDocument();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());
        when(mockSession.save(any())).thenReturn(1L);

        //Action
        Document actual = DR.add(document);

        //Assert
        Assert.assertTrue(actual.getId()==1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addDocumentWithNonExistingUser() {

        //Set Up
        Document document = TestHibernateData.getDocumentNoAuthor();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        doThrow(new OptimisticLockException()).when(mockQuery).list();

        //Action
        DR.add(document);
    }

    @Test
    public void updateDocumentSuccess(){

        //Set Up
        Document document = TestHibernateData.getDocument();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());

        //Action
        boolean actual = DR.update(document);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateDocumentWithWrongId(){

        //Set Up
        Document document = TestHibernateData.getDocumentWrongId();

        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnUserList()).thenReturn(TestHibernateData.getHbnTagList());
        doThrow(new OptimisticLockException()).when(mockSession).update(any());

        //Action
        DR.update(document);
    }

    @Test
    public void removeDocumentSuccess(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);

        //Action
        boolean actual = DR.remove(1);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void removeDocumentWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        doThrow(new OptimisticLockException()).when(mockSession).delete(any());

        //Action
        DR.remove(0);
    }

    @Test
    public void getDocumentSuccess() {

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getHbnDocumentList());

        //Action
        long actual = DR.getQuery(new GetDocumentByIdSpec(1)).get(0).getId();
        long expected = 1;

        //Assert
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getDocumentWithWrongId(){

        //Set Up
        when(mockSessionFactory.openSession()).thenReturn(mockSession);
        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(TestHibernateData.getEmptyHbnDocumentList());

        //Action
        DR.getQuery(new GetDocumentByIdSpec(0));
    }
}
