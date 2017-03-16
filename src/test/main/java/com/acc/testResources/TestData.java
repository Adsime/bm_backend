package main.java.com.acc.testResources;

import com.acc.models.*;
import org.junit.Before;
import org.mockito.Mock;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TestData {

    @Mock
    private static HttpHeaders headers;

    public static String credentials = "username:password";
    public static String badCredentials = "no:access";

    public static List<Group> testGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "asdasd",null, null, null));
        groups.add(new Group(2, "asdasd",null, null, null));
        groups.add(new Group(3, "asdasd",null, null, null));
        return groups;
    }

    public static List<User> testUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("David", "Silva", "davids@mcfc.co.uk", "melsom.adrian", "", null));
        users.add(new User("Adrian", "Melsom", "asdasdasd@asdasdad.com", "melsom.adrian", "", null));
        users.add(new User("Adrian", "Melsom", "asdasdasd@asdasdad.com", "melsom.adrian","" , null));
        return users;
    }

    public static HttpHeaders testCredentials() {
        headers = mock(HttpHeaders.class);
        List<String> authHeaders = new ArrayList<>();
        authHeaders.add(credentials);
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeaders);
        return headers;
    }

    public static HttpHeaders testBadCredentials() {
        headers = mock(HttpHeaders.class);
        List<String> authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeaders);
        return headers;
    }

    public static List<Problem> testProblems() {
        List<Problem> problems = new ArrayList<>();
        problems.add(new Problem(1, 1,"asdad", "asdasddsa", "path", null));
        problems.add(new Problem(2, 1,"asdad", "asdasddsa", "path", null));
        problems.add(new Problem(3, 1,"asdad", "asdasddsa", "path", null));
        problems.add(new Problem(4, 1,"asdad", "asdasddsa", "path", null));
        return problems;
    }

    public static List<Tag> testTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "name", "test", "testdata"));
        tags.add(new Tag(2, "name","test", "testdata"));
        tags.add(new Tag(3, "name","test", "testdata"));
        tags.add(new Tag(4, "name","test", "testdata"));
        return tags;

    }

    public static JsonObject jsonGroup() {
        return Json.createObjectBuilder()
                .add("name", "group 1")
                .add("school", "hioa")
                .add("id", 1)
                .build();
    }

    public static JsonObject jsonProblem() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        return Json.createObjectBuilder()
                .add("id", 1)
                .add("title", "problem 1")
                .add("content", "asdasdasd")
                .add("links", jab)
                .build();
    }

    public static JsonObject jsonTag() {
        return Json.createObjectBuilder()
                .add("tag", "testTag1")
                .build();
    }

    public static JsonObject jsonUser() {
        return Json.createObjectBuilder()
                .add("name", "name")
                .build();
    }
}
