package com.acc.testResources;

import com.acc.models.*;
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
        groups.add(new Group("1", "hioa", 1, testStudents(), testSupervisors()));
        groups.add(new Group("2", "ntnu", 1, testStudents(), testSupervisors()));
        groups.add(new Group("3", "uio", 1, testStudents(), testSupervisors()));
        return groups;
    }

    public static List<Student> testStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        students.add(new Student("Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        students.add(new Student("Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        students.add(new Student("Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));
        return students;
    }

    public static List<Supervisor> testSupervisors() {
        List<Supervisor> supervisors = new ArrayList<>();
        supervisors.add(new Supervisor(1));
        supervisors.add(new Supervisor(2));
        supervisors.add(new Supervisor(3));
        supervisors.add(new Supervisor(4));
        return supervisors;
    }

    public static List<User> testUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        users.add(new User("2", "Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        users.add(new User("3", "Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        users.add(new User("4", "Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));
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
        problems.add(new Problem("asdasd", "aasdasd", testUsers().get(0)));
        problems.add(new Problem("asdasdd", "aasdasdd", testUsers().get(1)));
        problems.add(new Problem("asdasddd", "aasdasddd", testUsers().get(2)));
        problems.add(new Problem("asdasdddd", "aasdasdddd", testUsers().get(3)));
        return problems;
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
}
