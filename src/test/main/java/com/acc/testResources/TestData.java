package main.java.com.acc.testResources;

import com.acc.database.pojo.Group;
import com.acc.database.pojo.Problem;
import com.acc.database.pojo.Tag;
import com.acc.database.pojo.User;
import org.junit.Before;
import org.mockito.Mock;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
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
        groups.add(new Group(1, "group1"));
        groups.add(new Group(2, "group2"));
        groups.add(new Group(3, "group3"));
        return groups;
    }

    public static List<User> testUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com", testProblems().get(0)));
        users.add(new User("Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com", testProblems().get(0)));
        users.add(new User("Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com", testProblems().get(0)));
        users.add(new User("Kim", "Vu", "vu.kim", "vu.kim@accenture.com", testProblems().get(0)));
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
        problems.add(new Problem("asdasd"));
        problems.add(new Problem("asdasdd"));
        problems.add(new Problem("asdasddd"));
        problems.add(new Problem("asdasdddd"));
        return problems;
    }

    public static List<Tag> testTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("TestTag1"));
        tags.add(new Tag("TestTag2"));
        tags.add(new Tag("TestTag3"));
        tags.add(new Tag("TestTag4"));
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
        return Json.createObjectBuilder()
                .add("title", "problem 1")
                .add("body", "asdasdasd")
                .add("user", "user")
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
