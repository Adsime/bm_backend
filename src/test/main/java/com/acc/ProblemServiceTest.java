package main.java.com.acc;

import com.acc.database.repository.ProblemRepository;
import com.acc.google.FileHandler;
import com.acc.models.Problem;
import com.acc.service.ProblemService;
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
public class ProblemServiceTest {
    @Mock
    ProblemRepository problemRepository;

    @Mock
    FileHandler fileHandler;

    ProblemService service;



    @Before
    public void setup() {
        initMocks(this);
        service = new ProblemService();
        service.problemRepository = problemRepository;
        service.fileHandler = fileHandler;
    }

    //Start getProblem()

    @Test
    public void getProblemSuccessTest() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        Problem expected = TestData.testProblems().get(0);
        Problem actual = service.getProblem(0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getProblemNoEntryException() {
        when(problemRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Problem expected = null;
        Problem actual = service.getProblem(0);
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getProblemInternalError() {
        when(problemRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getProblem(0);
    }

    //End getProblem()
    //Start getAllProblems()

    @Test
    public void getAllProblemsSuccess() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        String expected = TestData.testProblems().toString();
        String actual = service.getAllProblems().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProblemsEmptyList() {
        when(problemRepository.getQuery(any())).thenReturn(new ArrayList<Problem>());
        boolean expected = service.getAllProblems().isEmpty();
        assertTrue(expected);
    }

    @Test
    public void getAllProblemsNoSuchEntity() {
        when(problemRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllProblems();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getAllProblemsInternalError() {
        when(problemRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllProblems();
    }

    //End getAllProblems()
    //Start newProblem()

    @Test
    public void newProblemSuccess() {
        Problem problem = TestData.testProblems().get(0);
        when(problemRepository.add(any())).thenReturn(problem);
        when(fileHandler.createFile(anyString(), anyString(), anyList())).thenReturn(problem.getPath());
        String expected = problem.toString();
        String actual = service.newProblem(problem).toString();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void newProblemFail() {
        when(fileHandler.createFile(anyString(), anyString(), anyList())).thenReturn("");
        when(problemRepository.add(any())).thenThrow(new InternalServerErrorException());
        service.newProblem(TestData.testProblems().get(0));
    }

    //End newProblem()
    //Start deleteProblem()

    @Test
    public void deleteProblemSuccess() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(problemRepository.remove(anyLong())).thenReturn(true);
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        boolean acutal = service.deleteProblem(0);
        assertTrue(acutal);
    }

    @Test
    public void deleteProblemNoEntry() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(problemRepository.remove(anyLong())).thenReturn(false);
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        boolean actual = service.deleteProblem(0);
        assertFalse(actual);
    }

    @Test
    public void deleteProblemNoEntryNull() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(problemRepository.remove(anyLong())).thenThrow(new NoSuchEntityException());
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        boolean actual = service.deleteProblem(0);
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void deleteProblemInternalError() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(problemRepository.remove(anyLong())).thenThrow(new InternalServerErrorException());
        when(fileHandler.deleteFile(anyString())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        service.deleteProblem(0);
    }

    //End deleteProblem()
    //Start updateProblem()

    @Test
    public void updateProblemSuccess() {
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(problemRepository.update(any())).thenReturn(true);
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateProblem(TestData.testProblems().get(0));
        assertTrue(actual);
    }

    @Test
    public void updateProblemFail() {
        when(problemRepository.update(any())).thenReturn(false);
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateProblem(TestData.testProblems().get(0));
        assertFalse(actual);
    }

    @Test
    public void updateProblemNoEntry() {
        when(problemRepository.update(any())).thenThrow(new NoSuchEntityException());
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        boolean actual = service.updateProblem(TestData.testProblems().get(0));
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void updateProblemInternalError() {
        when(problemRepository.update(any())).thenThrow(new InternalServerErrorException());
        when(problemRepository.getQuery(any())).thenReturn(TestData.testProblems());
        when(fileHandler.insertFileContent(any())).thenReturn(TestData.testProblems().get(0));
        when(fileHandler.updateFile(anyString(), anyString(), anyString())).thenReturn(true);
        service.updateProblem(TestData.testProblems().get(0));
    }
}
