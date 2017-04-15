package com.acc.database.specification;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by melsom.adrian on 06.04.2017.
 */
public class GetGroupByTagSpec implements HqlSpecification {
    private static final String AND = "= ";
    private static final String OR = "> 0";
    private List<String> tags;
    private String operator;

    public GetGroupByTagSpec(List<String> tags, boolean hasAll) {
        this.tags = tags;
        this.operator = (hasAll) ? AND + tags.size() : OR;
    }

    @Override
    public String toHqlQuery() {
        String query = "select u from HbnBachelorGroup u join u.tags t WHERE ";
        StringJoiner sj = new StringJoiner(" in t.tagName) OR " +
                "(", "(", " in t.tagName) " +
                "GROUP BY u.id HAVING COUNT(u.id) " + operator);
        for(String s : tags) {
            sj.add("'"+s+"'");
        }
        query += sj.toString();
        System.out.println(query);
        return query;
    }
}
