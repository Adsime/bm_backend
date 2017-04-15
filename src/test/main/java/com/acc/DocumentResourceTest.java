package main.java.com.acc;

import com.acc.models.Document;
import com.acc.service.DocumentService;
import com.acc.resources.DocumentResource;
import main.java.com.acc.testResources.TestData;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.InternalServerErrorException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

public class DocumentResourceTest {

    @Mock
    private DocumentService service;

    public DocumentResource documentResource;

    int expected, actual;

    @Before
    public void setup() {
        initMocks(this);
        documentResource = new DocumentResource();
    }

    // Start getHbnDocument Tests

    @Test
    public void getDocumentSuccess() {
        documentResource.service = service;
        when(service.getDocument(0)).thenReturn(TestData.testDocuments().get(0));
        expected = HttpStatus.OK_200;
        actual = documentResource.getDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getDocumentNoEntries() {
        documentResource.service = service;
        when(service.getDocument(0)).thenReturn(null);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = documentResource.getDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getDocumentInternalError() {
        documentResource.service = service;
        when(service.getDocument(0)).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = documentResource.getDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getHbnDocument Tests
    // Start getAllDocuments Tests

    @Test
    public void getAllDocumentsSuccess() {
        documentResource.service = service;
        when(service.getAllDocuments()).thenReturn(TestData.testDocuments());
        expected = HttpStatus.OK_200;
        actual = documentResource.getAllDocuments(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDocumentsNoEntries() {
        documentResource.service = service;
        when(service.getAllDocuments()).thenReturn(new ArrayList<Document>());
        expected = HttpStatus.BAD_REQUEST_400;
        actual = documentResource.getAllDocuments(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDocumentsInternalError() {
        documentResource.service = service;
        when(service.getAllDocuments()).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = documentResource.getAllDocuments(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getAllDocuments Tests
    // Start newDocument Tests

    @Test
    public void newDocumentsSuccess() {
        documentResource.service = service;
        when(service.newDocument(any())).thenReturn(TestData.testDocuments().get(0));
        expected = HttpStatus.CREATED_201;
        actual = documentResource.newDocument(TestData.testCredentials(), TestData.jsonDocument()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newDocumentsInternalError() {
        documentResource.service = service;
        when(service.newDocument(any())).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = documentResource.newDocument(TestData.testCredentials(), TestData.jsonDocument()).getStatus();
        assertEquals(expected, actual);
    }

    // End newDocument Tests
    // Start updateDocument Tests

    @Test
    public void updateDocumentsSuccess() {
        documentResource.service = service;
        when(service.updateDocument(any())).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = documentResource.updateDocument(TestData.testCredentials(), TestData.jsonDocument()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateDocumentsNoEntries() {
        documentResource.service = service;
        when(service.updateDocument(any())).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = documentResource.updateDocument(TestData.testCredentials(), TestData.jsonDocument()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateDocumentsInternalError() {
        documentResource.service = service;
        when(service.updateDocument(any())).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = documentResource.updateDocument(TestData.testCredentials(), TestData.jsonDocument()).getStatus();
        assertEquals(expected, actual);
    }

    // End updateDocument Tests
    // Start deleteDocument Tests

    @Test
    public void deleteDocumentsSuccess() {
        documentResource.service = service;
        when(service.deleteDocument(0)).thenReturn(true);
        expected = HttpStatus.NO_CONTENT_204;
        actual = documentResource.deleteDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteDocumentsNoEntries() {
        documentResource.service = service;
        when(service.deleteDocument(0)).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = documentResource.deleteDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteDocumentsInternalError() {
        documentResource.service = service;
        when(service.deleteDocument(0)).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = documentResource.deleteDocument(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    //End deleteDocument Tests
}
