package com.acc.service;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.google.FileHandler;
import com.acc.models.Problem;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
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
            Problem problem = problemRepository.getQuery(new GetProblemByIdSpec((long)id)).get(0);
            if(problem == null) return null;
            return fileHandler.insertFileContent(problem);
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return null;
    }

    public List<Problem> getAllProblems() {
        try {
            return problemRepository.getQuery(new GetProblemAllSpec());
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return Arrays.asList();
    }

    public Problem newProblem(Problem problem) {
        String path = fileHandler.createFile(problem.getTitle(), problem.getContent(), Arrays.asList("0ByI1HjM5emiFcXRtSVplQmJ6YjA"));
        if(path != null) {
            problem.setPath(path);
            try {
                return problemRepository.add(problem);
            } catch (EntityNotFoundException enfe) {
                fileHandler.deleteFile(path);
                return null;
            }

        }
        return null;
    }

    public boolean deleteProblem(int id) {
        try {
            Problem problem = getProblem(id);
            if(problem == null) return false;
            boolean deleted = problemRepository.remove(id);
            if(deleted) {
                fileHandler.deleteFile(problem.getPath());
            }
            return deleted;
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;

    }

    public boolean updateProblem(Problem problem) {
        try {
            Problem old = getProblem(problem.getId());
            if(old == null) {
                return false;
            }
            boolean updated = problemRepository.update(problem);
            if(updated) {
                fileHandler.updateFile(old.getPath(), problem.getTitle(), problem.getContent());
            }
            return updated;
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;
    }
}
