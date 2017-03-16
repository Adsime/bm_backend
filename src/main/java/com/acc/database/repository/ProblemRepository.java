package com.acc.database.repository;

import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnProblem;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Problem;
import com.acc.providers.Links;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 15.03.2017 NO AUTO INCREMENT OF PROBLEM
public class ProblemRepository extends AbstractRepository implements Repository<Problem> {
        
    public ProblemRepository() {
        super();
    }

    @Override
    public Problem add(Problem problem) {

        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()));
        if (problem.getTags() != null) mappedProblem.setTags(super.toHbnTagSet(problem.getTags()));
        long id = super.addEntity(mappedProblem);

        Problem addedProblem = new Problem(
                (int) id,
                problem.getAuthor(),
                problem.getTitle(),
                problem.getContent(),
                problem.getPath(),
                problem.getTags()
        );
        return addedProblem;
    }

    @Override
    public boolean update(Problem problem) {
        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()));
        mappedProblem.setId(problem.getId());
        if (problem.getTags() != null) mappedProblem.setTags(super.toHbnTagSet(problem.getTags()));
        return super.updateEntity(mappedProblem);
    }

    @Override
    public boolean remove(long id) {
        HbnProblem mappedProblem = new HbnProblem();
        mappedProblem.setId(id);
        return super.removeEntity(mappedProblem);
    }

    @Override
    public List<Problem> getQuery(Specification spec) {
        List<HbnEntity> readData = super.queryToDb((HqlSpecification) spec);
        List<Problem> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnProblem hbnProblem = (HbnProblem) entity;

            Problem problem = new Problem(
                    (int)hbnProblem.getId(),
                    (int)hbnProblem.getUser().getId(),
                    "",
                    "",
                    hbnProblem.getPath(),
                    hbnProblem.getTags() != null ? super.toTagList(hbnProblem.getTags()) : new ArrayList<>()
            );

            List<Integer> authorId = new ArrayList<>(problem.getAuthor());
            problem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            problem.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, problem.getTagIdList()));
            result.add(problem);
        }
        return result;
    }

    private HbnUser getAuthor(long authorId){
        return (HbnUser) queryToDb(new GetUserByIdSpec(authorId)).get(0);
    }
}

