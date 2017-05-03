package com.acc.database.specification;

/**
 * Created by nguyen.duy.j.khac on 03.05.2017.
 */
public class GetDocumentWithPathSpec implements HqlSpecification {
    private String path;

    public GetDocumentWithPathSpec(final String path){
        this.path = path;
    }

    @Override
    public String toHqlQuery() {
        return "FROM HbnDocument WHERE path='" + path + "'";
    }
}

