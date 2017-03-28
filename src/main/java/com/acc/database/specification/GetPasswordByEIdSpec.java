package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 28.03.2017.
 */
public class GetPasswordByEIdSpec implements HqlSpecification{
    private String eid;

    public GetPasswordByEIdSpec(String eid){
        this.eid = eid;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnPassword WHERE eid_hash='" + eid + "'";
    }
}
