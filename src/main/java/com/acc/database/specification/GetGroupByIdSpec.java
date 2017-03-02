package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 23.02.2017.
 */
public class GetGroupByIdSpec implements HqlSpecification{

    private long id;

    public GetGroupByIdSpec(long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnBachelorGroup WHERE id='" + id + "'";
    }
}
