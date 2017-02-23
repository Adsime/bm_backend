package com.acc.providers;

import com.acc.models.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 23.02.2017.
 */
public class Links {

    public static final String USER_LINK = "/users";
    public static final String GROUP_LINK = "/groups";
    public static final String TAG_LINK = "/tags";
    public static final String PROBLEM_LINK = "/problems";

    public static final String USER = "user";
    public static final String GROUP = "group";
    public static final String TAG = "tag";
    public static final String PROBLEM = "problem";

    public static final String USERS = "users";
    public static final String GROUPS = "groups";
    public static final String TAGS = "tags";
    public static final String PROBLEMS = "problems";

    public static List<Link> generateLinks(String type, List<Integer> ids) {
        List<Link> retVal = new ArrayList<>();
        switch (type) {
            case USER: {
                for(int id : ids) {
                    retVal.add(new Link(type, USER_LINK + id));
                }
                break;
            } case GROUP: {
                for(int id : ids) {
                    retVal.add(new Link(type, GROUP_LINK + id));
                }
                break;
            } case TAG: {
                for(int id : ids) {
                    retVal.add(new Link(type, TAG_LINK + id));
                }
                break;
            } case PROBLEM: {
                for(int id : ids) {
                    retVal.add(new Link(type, PROBLEM_LINK + id));
                }
                break;
            }
        }
        return retVal;
    }
}
