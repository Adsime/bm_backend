package main.java.com.acc.testResources;

import com.acc.models.*;
import org.mockito.Mock;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        users.add(new User("David", "Silva", "davids@mcfc.co.uk", "99999999", "melsom.adrian", "", null));
        users.add(new User("Adrian", "Melsom", "asdasdasd@asdasdad.com", "99999999","melsom.adrian",  "", null));
        users.add(new User("Adrian", "Melsom", "asdasdasd@asdasdad.com", "99999999","melsom.adrian","" , null));
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

    public static List<Document> testDocuments() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document(1, 1,"asdad", "asdasddsa", "path", null));
        documents.add(new Document(2, 1,"asdad", "asdasddsa", "path", null));
        documents.add(new Document(3, 1,"asdad", "asdasddsa", "path", null));
        documents.add(new Document(4, 1,"asdad", "asdasddsa", "path", null));
        return documents;
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

    public static JsonObject jsonDocument() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        return Json.createObjectBuilder()
                .add("id", 1)
                .add("title", "document 1")
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
