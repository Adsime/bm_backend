package com.acc.database.repository;

import com.acc.database.pojo.HbnPassword;
import com.acc.database.pojo.HbnProblem;
import com.acc.database.pojo.HbnUser;
import com.acc.models.Problem;
import com.sun.org.apache.xalan.internal.xsltc.dom.MultiValuedNodeHeapIterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */
public class ProblemRepository extends AbstractRepository<HbnProblem> implements IRepository<HbnProblem>{

    public ProblemRepository(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    public boolean addAndMap(Problem problem) {

        HbnProblem mappedProblem = new HbnProblem(
                "path", new HbnUser("Håkon", "Smørvik","Email","Salt")
                //problem.getPath);
        );
        return super.add(mappedProblem);
    }

    public boolean update(Problem problem) {

        Session session = super.getSessionFactory().openSession();
        HbnProblem mappedProblem = new HbnProblem();
        Transaction tx = null;

        try {
            session.load(HbnProblem.class,problem.getId());
            //mappedProblem.setPath();

        }
        catch (HibernateException he) {

            if (tx!=null) tx.rollback();
            he.printStackTrace();
            return false;
        }
        finally {

            session.close();
            return true;
        }
    }

    public boolean remove(Problem problem) {
        return false;
    }


}
