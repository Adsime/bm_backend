package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 16.02.2017.
 */
public class GetDocumentByIdSpec implements HqlSpecification {
    private long id;

    public GetDocumentByIdSpec(final long id){
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnDocument WHERE id='" + id + "'";
    }
}
