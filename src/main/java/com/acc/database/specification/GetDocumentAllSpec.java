package com.acc.database.specification;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by nguyen.duy.j.khac on 21.02.2017.
 */
public class GetDocumentAllSpec implements HqlSpecification  {


    List<String> tags;

    public GetDocumentAllSpec(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toHqlQuery() {
        String query = "select doc FROM HbnDocument doc join doc.tags t where ";
        StringJoiner sj = new StringJoiner(" in t.tagName) OR (",
                "(",
                " in t.tagName) GROUP BY doc.id HAVING COUNT(doc.id) >= " + tags.size() );
        tags.forEach(s -> sj.add("'"+s+"'"));
        query += sj.toString();
        return query;
    }
}
