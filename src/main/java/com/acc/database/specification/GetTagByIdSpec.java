package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 23.02.2017.
 */
public class GetTagByIdSpec implements HqlSpecification{
    private long id;

    public GetTagByIdSpec (final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnTag WHERE id='" + id + "'";
    }

}
