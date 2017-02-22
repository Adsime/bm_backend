package com.acc.service;

import com.acc.database.repository.ProblemRepository;
import com.acc.models.Problem;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class ProblemService extends GeneralService{

    @Inject
    public ProblemRepository problemRepository;

    public Problem getProblem(int id) {
        return null;
    }

    public List<Problem> getAllProblems() {
        return null;
    }

    public boolean newProblem(Problem problem) {
        return problemRepository.add(problem);
    }

    public boolean deleteProblem(int id) {
        return true;
    }

    public boolean updateProblem(Problem problem) {
        return true;
    }
}
