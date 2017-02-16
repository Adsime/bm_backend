package com.acc.database.repository;

import com.acc.database.pojo.HbnProblem;
import com.acc.database.pojo.HbnUser;
import com.acc.database.specification.Specification;
import com.acc.models.Problem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class ProblemRepository extends AbstractRepository<HbnProblem> implements IRepository<Problem> {

    public ProblemRepository() {
        super();
    }

    @Override
    public boolean add(Problem problem) {

        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()));
        return super.addToDb(mappedProblem);
    }

    @Override
    public boolean update(Problem problem) {
        return false;
    }

    @Override
    public boolean remove(Problem problem) {
        return false;
    }

    @Override
    public List<Problem> query(Specification specification) {
        return null;
    }

    private HbnUser getAuthor(long authorId){

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
    }
}

