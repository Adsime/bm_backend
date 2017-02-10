package test.testResources;

import com.acc.models.Group;
import com.acc.models.Student;
import com.acc.models.Supervisor;
import com.acc.models.User;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.mockito.Mock;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class TestData {

    @Mock
    private static HttpHeaders headers;

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
        List<String> authHeaders = new ArrayList<>();
        authHeaders.add("username:password");
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeaders);
        return headers;
    }

    public static JsonObject jsonGroup() {
        return new JsonGenerator().;
    }

    public static HttpHeaders testBadCredentials() {
        List<String> authHeaders = new ArrayList<>();
        authHeaders.add("no:access");
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeaders);
        return headers;
    }
}
