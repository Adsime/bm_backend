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
    private List<Integer> tags;
    private String operator;

    public GetUserByTagSpec(List<Integer> tags, boolean hasAll) {
        this.tags = tags;
        this.operator = (hasAll) ? AND + tags.size() : OR;
    }

    @Override

    public String toHqlQuery() {
        String query = "select u from HbnUser u join u.tags t WHERE ";
        StringJoiner sj = new StringJoiner(" in t.id) OR " +
                "(", "(", " in t.id) " +
                "GROUP BY u.id HAVING COUNT(u.id) " + operator);
        for(Integer i : tags) {
            sj.add("'"+i.intValue()+"'");
        }
        query += sj.toString();
        System.out.println(query);
        return query;
    }
}
