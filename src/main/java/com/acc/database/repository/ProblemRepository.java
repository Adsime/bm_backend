package com.acc.database.repository;

import com.acc.database.pojo.HbnProblem;
import com.acc.database.pojo.HbnTag;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Link;
import com.acc.models.Problem;
import com.acc.models.Tag;
import com.acc.providers.Links;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class ProblemRepository extends AbstractRepository<HbnProblem> implements Repository<Problem> {

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
        List<Integer> authorId = new ArrayList<>((int)mappedProblem.getUser().getId());
        addedProblem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
        addedProblem.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, problem.getTagIdList()));
        return addedProblem;
    }

    @Override
    public boolean update(Problem problem) {
        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()));
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
        List<HbnProblem> readData = super.queryFromDb((HqlSpecification) spec);
        List<Problem> result = new ArrayList<>();

        for (HbnProblem readProblem : readData){
            Problem problem = new Problem(
                    (int)readProblem.getId(),
                    (int)readProblem.getUser().getId(),
                    "",
                    "",
                    readProblem.getPath(),
                    super.toTagList(readProblem.getTags())
            );

            List<Integer> authorId = new ArrayList<>(problem.getAuthor());
            problem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            problem.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, problem.getTagIdList()));
        }
        return result;
    }

    private HbnUser getAuthor(long authorId){
        return (HbnUser) queryByIdSpec(new GetUserByIdSpec(authorId));
    }
}

