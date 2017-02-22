package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 22.02.2017.
 */
public class GetUserAllSpec implements HqlSpecification{

    public GetUserAllSpec(){}

    @Override
    public String toHqlQuery() {
        return "FROM HbnUser";
    }
}
