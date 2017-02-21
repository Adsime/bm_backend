package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 16.02.2017.
 */
public class GetProblemByIdSpec implements HqlSpecification {
    private long id;

    public GetProblemByIdSpec(final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnProblem WHERE id='" + id + "'";

    }
}
