package main.java.com.acc;

import com.acc.models.Problem;
import com.acc.service.ProblemService;
import com.acc.resources.ProblemResource;
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

public class ProblemResourceTest {

    @Mock
    private ProblemService service;

    public ProblemResource problemResource;

    int expected, actual;

    @Before
    public void setup() {
        initMocks(this);
        problemResource = new ProblemResource();
    }

    // Start getProblem Tests

    @Test
    public void getProblemSuccess() {
        problemResource.service = service;
        when(service.getProblem(0)).thenReturn(TestData.testProblems().get(0));
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = problemResource.getProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getProblemAuthFail() {
        problemResource.service = service;
        when(service.getProblem(0)).thenReturn(TestData.testProblems().get(0));
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = problemResource.getProblem(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getProblemNoEntries() {
        problemResource.service = service;
        when(service.getProblem(0)).thenReturn(null);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = problemResource.getProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getProblemInternalError() {
        problemResource.service = service;
        when(service.getProblem(0)).thenThrow(new InternalServerErrorException());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = problemResource.getProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getProblem Tests
    // Start getAllProblems Tests

    @Test
    public void getAllProblemsSuccess() {
        problemResource.service = service;
        when(service.getAllProblems()).thenReturn(TestData.testProblems());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = problemResource.getAllProblems(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProblemsAuthFail() {
        problemResource.service = service;
        when(service.getAllProblems()).thenReturn(TestData.testProblems());
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = problemResource.getAllProblems(TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProblemsNoEntries() {
        problemResource.service = service;
        when(service.getAllProblems()).thenReturn(new ArrayList<Problem>());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = problemResource.getAllProblems(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProblemsInternalError() {
        problemResource.service = service;
        when(service.getAllProblems()).thenThrow(new InternalServerErrorException());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = problemResource.getAllProblems(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getAllProblems Tests
    // Start newProblem Tests

    @Test
    public void newProblemsSuccess() {
        problemResource.service = service;
        when(service.newProblem(any())).thenReturn(true);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.CREATED_201;
        actual = problemResource.newProblem(TestData.testCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newProblemsAuthFail() {
        problemResource.service = service;
        when(service.newProblem(TestData.testProblems().get(0))).thenReturn(true);
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = problemResource.newProblem(TestData.testBadCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newProblemsInternalError() {
        problemResource.service = service;
        when(service.newProblem(any())).thenThrow(new InternalServerErrorException());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = problemResource.newProblem(TestData.testCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    // End newProblem Tests
    // Start updateProblem Tests

    @Test
    public void updateProblemsSuccess() {
        problemResource.service = service;
        when(service.updateProblem(any())).thenReturn(true);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = problemResource.updateProblem(TestData.testCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateProblemsAuthFail() {
        problemResource.service = service;
        when(service.updateProblem(any())).thenReturn(true);
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = problemResource.updateProblem(TestData.testBadCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateProblemsNoEntries() {
        problemResource.service = service;
        when(service.updateProblem(any())).thenReturn(false);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = problemResource.updateProblem(TestData.testCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateProblemsInternalError() {
        problemResource.service = service;
        when(service.updateProblem(any())).thenThrow(new InternalServerErrorException());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = problemResource.updateProblem(TestData.testCredentials(), TestData.jsonProblem()).getStatus();
        assertEquals(expected, actual);
    }

    // End updateProblem Tests
    // Start deleteProblem Tests

    @Test
    public void deleteProblemsSuccess() {
        problemResource.service = service;
        when(service.deleteProblem(0)).thenReturn(true);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.NO_CONTENT_204;
        actual = problemResource.deleteProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteProblemsAuthFail() {
        problemResource.service = service;
        when(service.deleteProblem(0)).thenReturn(true);
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = problemResource.deleteProblem(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteProblemsNoEntries() {
        problemResource.service = service;
        when(service.deleteProblem(0)).thenReturn(false);
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = problemResource.deleteProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteProblemsInternalError() {
        problemResource.service = service;
        when(service.deleteProblem(0)).thenThrow(new InternalServerErrorException());
        when(service.verify(TestData.credentials)).thenReturn(true);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = problemResource.deleteProblem(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    //End deleteProblem Tests
}
