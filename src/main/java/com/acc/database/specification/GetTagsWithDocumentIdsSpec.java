package com.acc.database.specification;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by melsom.adrian on 05.05.2017.
 */
public class GetTagsWithDocumentIdsSpec implements HqlSpecification {

    private String id;

    public GetTagsWithDocumentIdsSpec(String id) {
        this.id = id;
    }

    @Override
    public String toHqlQuery() {
        String query = "select tags from HbnDocument doc join doc.tags tags where doc.path = '" + id + "'";
        return query;
    }
}
