package com.acc.service;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.google.FileHandler;
import com.acc.models.Problem;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class ProblemService extends GeneralService {

    @Inject
    public FileHandler fileHandler;

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
        String path = fileHandler.createFile(problem.getTitle(), problem.getContent(), Arrays.asList("0ByI1HjM5emiFcXRtSVplQmJ6YjA"));
        if(path != null) {
            problem.setPath(path);
            return problemRepository.add(problem);
        }
        return null;
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
