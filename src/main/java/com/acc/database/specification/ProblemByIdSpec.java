package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 16.02.2017.
 */
public class ProblemByIdSpec implements HqlSpecification {
    private long id;

    public ProblemByIdSpec(final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "from HbnProblem";
    }
}
