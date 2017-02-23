package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 23.02.2017.
 */
public class GetGroupAllSpec implements HqlSpecification{

    public GetGroupAllSpec(){}

    @Override
    public String toHqlQuery() {
        return "FROM HbnGroup";
    }
}
