package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 16.02.2017.
 */
public class ProblemByIdSpecification implements HqlSpecification {
    private long id;

    public ProblemByIdSpecification(final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return String.format("FROM PROBLEM WHERE id= :id");
    }
}
