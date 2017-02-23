package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 23.02.2017.
 */
public class GetTagAllSpec implements HqlSpecification {

    public GetTagAllSpec(){}

    @Override
    public String toHqlQuery() {
        return "FROM HbnTag";
    }
}
