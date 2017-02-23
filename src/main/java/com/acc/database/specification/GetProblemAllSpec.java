package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 21.02.2017.
 */
public class GetProblemAllSpec implements HqlSpecification  {

    @Override
    public String toHqlQuery() {
        return "FROM HbnProblem";
    }
}
