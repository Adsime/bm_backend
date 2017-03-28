package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 28.03.2017.
 */
public class GetUserByEIdSpec implements HqlSpecification{
    private String eid;

    public GetUserByEIdSpec(String eid){
        this.eid = eid;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnUser WHERE enterprise_id='" + eid + "'";
    }
}

