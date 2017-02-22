package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 22.02.2017.
 */
public class GetUserByIdSpec implements HqlSpecification{
    private long id;

    public GetUserByIdSpec (final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnUser WHERE id='" + id + "'";
    }
}
