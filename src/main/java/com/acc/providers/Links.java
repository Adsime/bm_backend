package com.acc.providers;

import com.acc.models.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 23.02.2017.
 */
public class Links {

    public static final String USER_LINK = "/users";
    public static final String SUPERVISOR_LINK = "/supervisors";
    public static final String STUDENT_LINK = "/students";
    public static final String GROUP_LINK = "/groups";
    public static final String TAG_LINK = "/tags";
    public static final String DOCUMENT_LINK = "/documents";

    public static final String USER = "user";
    public static final String STUDENT = "student";
    public static final String SUPERVISOR = "supervisor";
    public static final String GROUP = "group";
    public static final String TAG = "tag";
    public static final String DOCUMENT = "document";

    public static final String USERS = "users";
    public static final String STUDENTS = "students";
    public static final String SUPERVISORS = "supervisors";
    public static final String GROUPS = "groups";
    public static final String TAGS = "tags";
    public static final String DOCUMENTS = "documents";

    private static final String COMBINE = "/";

    public static List<Link> generateLinks(String type, List<Integer> ids) {
        List<Link> retVal = new ArrayList<>();
        switch (type) {
            case USER: {
                ids.forEach(id -> retVal.add(new Link(type, USER_LINK + COMBINE + id)));
                /*for(int id : ids) {
                    retVal.add(new Link(type, USER_LINK + COMBINE + id));
                }*/
                break;
            } case GROUP: {
                ids.forEach(id -> retVal.add(new Link(type, GROUP_LINK + COMBINE + id)));
                /*for(int id : ids) {
                    retVal.add(new Link(type, GROUP_LINK + COMBINE + id));
                }*/
                break;
            } case TAG: {
                ids.forEach(id -> retVal.add(new Link(type, TAG_LINK + COMBINE + id)));
                /*for(int id : ids) {
                    retVal.add(new Link(type, TAG_LINK + COMBINE + id));
                }*/
                break;
            } case DOCUMENT: {
                for(int id : ids) {
                    retVal.add(new Link(type, DOCUMENT_LINK + COMBINE + id));
                }
                break;
            } case STUDENT: {
                ids.forEach(id -> retVal.add(new Link(type, STUDENT_LINK + COMBINE + id)));
                /*for(int id : ids) {
                    retVal.add(new Link(type, USER_LINK + COMBINE + id));
                }*/
                break;
            }case SUPERVISOR: {
                ids.forEach(id -> retVal.add(new Link(type, SUPERVISOR_LINK + COMBINE + id)));
                /*for(int id : ids) {
                    retVal.add(new Link(type, USER_LINK + COMBINE + id));
                }*/
                break;
            }
        }
        return retVal;
    }
}
