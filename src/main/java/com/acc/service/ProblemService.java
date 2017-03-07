package com.acc.service;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.models.Problem;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class ProblemService extends GeneralService{

    @Inject
    public ProblemRepository problemRepository;

    public Problem getProblem(int id) {
        try {
            return problemRepository.getQuery(new GetProblemByIdSpec((long)id)).get(0);
        } catch (NoSuchEntityException nsee) {
            return null;
        }
    }

    public List<Problem> getAllProblems() {
        try {
            return problemRepository.getQuery(new GetProblemAllSpec());
        } catch (NoSuchEntityException nsee) {
            return Arrays.asList();
        }
    }

    public Problem newProblem(Problem problem) {
        return problemRepository.add(problem);
    }

    public boolean deleteProblem(int id) {
        try {
            return problemRepository.remove((long)id);
        } catch (NoSuchEntityException nsee) {
            return false;
        }

    }

    public boolean updateProblem(Problem problem) {
        try {
            return problemRepository.update(problem);
        } catch (NoSuchEntityException nsee) {
            return false;
        }
    }
}
