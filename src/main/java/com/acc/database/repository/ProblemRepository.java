package com.acc.database.repository;

import com.acc.database.pojo.HbnProblem;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class ProblemRepository extends AbstractRepository<HbnProblem> implements Repository<Problem> {

    public ProblemRepository() {
        super();
    }

    @Override
    public boolean add(Problem problem) {

       // HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()));
        return false;//super.addToDb(mappedProblem);
    }

    @Override
    public boolean update(Problem problem) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public List<Problem> getQuery(Specification spec) {
        List<HbnProblem> readData = super.queryFromDb((HqlSpecification) spec);
        List<Problem> result = new ArrayList<>();

        for (HbnProblem readProblem : readData){
            result.add( new Problem(
                    (int)readProblem.getId(),
                    (int)readProblem.getUser().getId(),
                    "",
                    "",
                    readProblem.getPath(),
                    null));
        }

        return result;
    }

    /*private HbnUser getAuthor(long authorId){

        Session session = super.getSessionFactory().openSession();
        HbnUser author = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            author = session.load(HbnUser.class, authorId);

        } catch (HibernateException he) {

            if (tx.getStatus() == TransactionStatus.ACTIVE) tx.rollback();
            he.printStackTrace();

        } finally {
            session.close();
            return author;
        }
    }*/
}

