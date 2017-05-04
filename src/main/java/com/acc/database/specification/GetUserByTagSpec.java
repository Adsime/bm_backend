package com.acc.database.specification;

import com.acc.models.Tag;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by melsom.adrian on 10.03.2017.
 */
public class GetUserByTagSpec implements HqlSpecification {
    private static final String AND = "= ";
    private static final String OR = "> 0";
    private List<String> tags;
    private String operator;

    public GetUserByTagSpec(List<String> tags, boolean hasAll) {
        this.tags = tags;
        this.operator = (hasAll) ? AND + tags.size() : OR;
    }

    @Override

    public String toHqlQuery() {
        String query = "select u from HbnUser u join u.tags t WHERE ";
        StringJoiner sj = new StringJoiner(" in t.tagName) OR (",
                "(",
                " in t.tagName) GROUP BY u.id HAVING COUNT(u.id) " + operator);
        for(String s : tags) {
            sj.add("'"+s+"'");
        }
        query += sj.toString();
        System.out.println(query);
        return query;
    }
}
