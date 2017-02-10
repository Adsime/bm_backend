package com.acc;

import com.acc.controller.GroupService;
import com.acc.resources.ProblemResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

public class ProblemResourceTest {

    @Mock
    private GroupService controller;

    public ProblemResource problemResource;

    @Before
    public void setup() {
        initMocks(this);
        problemResource = new ProblemResource();
    }

    // Start getProblem Tests

    @Test
    public void getProblemSuccess() {

    }

    @Test
    public void getProblemAuthFail() {

    }

    @Test
    public void getProblemNoEntries() {

    }

    @Test
    public void getProblemInternalError() {

    }

    // End getProblem Tests
    // Start getAllProblems Tests

    @Test
    public void getAllProblemsSuccess() {

    }

    @Test
    public void getAllProblemsAuthFail() {

    }

    @Test
    public void getAllProblemsNoEntries() {

    }

    @Test
    public void getAllProblemsInternalError() {

    }

    // End getAllProblems Tests
    // Start newProblem Tests

    @Test
    public void newProblemsSuccess() {

    }

    @Test
    public void newProblemsAuthFail() {

    }

    @Test
    public void newProblemsNoEntries() {

    }

    @Test
    public void newProblemsInternalError() {

    }

    // End newProblem Tests
    // Start updateProblem Tests

    @Test
    public void updateProblemsSuccess() {

    }

    @Test
    public void updateProblemsAuthFail() {

    }

    @Test
    public void updateProblemsNoEntries() {

    }

    @Test
    public void updateProblemsInternalError() {

    }

    // End updateProblem Tests
    // Start deleteProblem Tests

    @Test
    public void deleteProblemsSuccess() {

    }

    @Test
    public void deleteProblemsAuthFail() {

    }

    @Test
    public void deleteProblemsNoEntries() {

    }

    @Test
    public void deleteProblemsInternalError() {

    }

    //End deleteProblem Tests
}
