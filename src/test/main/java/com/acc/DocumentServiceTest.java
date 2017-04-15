package main.java.com.acc;

import com.acc.database.repository.DocumentRepository;
import com.acc.google.FileHandler;
import com.acc.models.Document;
import com.acc.service.DocumentService;
import main.java.com.acc.testResources.TestData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ejb.NoSuchEntityException;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class DocumentServiceTest {
    @Mock
    DocumentRepository documentRepository;

    @Mock
    FileHandler fileHandler;

    DocumentService service;



    @Before
    public void setup() {
        initMocks(this);
        service = new DocumentService();
        service.documentRepository = documentRepository;
        service.fileHandler = fileHandler;
    }

    //Start getDocument()

    @Test
    public void getDocumentSuccessTest() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        Document expected = TestData.testDocuments().get(0);
        Document actual = service.getDocument(0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getDocumentNoEntryException() {
        when(documentRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Document expected = null;
        Document actual = service.getDocument(0);
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getDocumentInternalError() {
        when(documentRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getDocument(0);
    }

    //End getDocument()
    //Start getAllDocuments()

    @Test
    public void getAllDocumentsSuccess() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        String expected = TestData.testDocuments().toString();
        String actual = service.getAllDocuments().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDocumentsEmptyList() {
        when(documentRepository.getQuery(any())).thenReturn(new ArrayList<>());
        boolean expected = service.getAllDocuments().isEmpty();
        assertTrue(expected);
    }

    @Test
    public void getAllDocumentsNoSuchEntity() {
        when(documentRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllDocuments();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getAllDocumentsInternalError() {
        when(documentRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllDocuments();
    }

    //End getAllDocuments()
    //Start newDocument()

    @Test
    public void newDocumentSuccess() {
        Document document = TestData.testDocuments().get(0);
        when(documentRepository.add(any())).thenReturn(document);
        when(fileHandler.createFile(anyString(), anyString(), anyList())).thenReturn(document.getPath());
        String expected = document.toString();
        String actual = service.newDocument(document).toString();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void newDocumentFail() {
        when(fileHandler.createFile(anyString(), anyString(), anyList())).thenReturn("");
        when(documentRepository.add(any())).thenThrow(new InternalServerErrorException());
        service.newDocument(TestData.testDocuments().get(0));
    }

    //End newDocument()
    //Start deleteDocument()

    @Test
    public void deleteDocumentSuccess() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(documentRepository.remove(anyLong())).thenReturn(true);
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        boolean acutal = service.deleteDocument(0);
        assertTrue(acutal);
    }

    @Test
    public void deleteDocumentNoEntry() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(documentRepository.remove(anyLong())).thenReturn(false);
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        boolean actual = service.deleteDocument(0);
        assertFalse(actual);
    }

    @Test
    public void deleteDocumentNoEntryNull() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(documentRepository.remove(anyLong())).thenThrow(new NoSuchEntityException());
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        boolean actual = service.deleteDocument(0);
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void deleteDocumentInternalError() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(documentRepository.remove(anyLong())).thenThrow(new InternalServerErrorException());
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        service.deleteDocument(0);
    }

    //End deleteDocument()
    //Start updateDocument()

    @Test
    public void updateDocumentSuccess() {
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(documentRepository.update(any())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateDocument(TestData.testDocuments().get(0));
        assertTrue(actual);
    }

    @Test
    public void updateDocumentFail() {
        when(documentRepository.update(any())).thenReturn(false);
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateDocument(TestData.testDocuments().get(0));
        assertFalse(actual);
    }

    @Test
    public void updateDocumentNoEntry() {
        when(documentRepository.update(any())).thenThrow(new NoSuchEntityException());
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateDocument(TestData.testDocuments().get(0));
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void updateDocumentInternalError() {
        when(documentRepository.update(any())).thenThrow(new InternalServerErrorException());
        when(documentRepository.getQuery(any())).thenReturn(TestData.testDocuments());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testDocuments().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        service.updateDocument(TestData.testDocuments().get(0));
    }
}
