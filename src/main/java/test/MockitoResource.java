package test;

import com.acc.controller.Controller;
import com.acc.database.DbHandler;
import com.acc.models.Group;
import com.acc.models.User;
import org.junit.Before;
import org.mockito.Mock;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class MockitoResource /*extends JerseyTest*/ {

    @Mock
    public DbHandler dbHandlerMock;
    @Mock
    public Controller controller;
    @Mock
    public HttpHeaders headers;

    public List<String> authHeaders;

    public String credentials;
    public String badCredentials;
    public List<User> users;
    public List<Group> groups;
    public JsonObject jsonGroup;

    @Before
    public void create() {

        // Initiate data used by mocks
        credentials = "username:password";
        badCredentials = "no:access";

        users = new ArrayList<>();
        users.add(new User("1", "Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        users.add(new User("2", "Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        users.add(new User("3", "Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        users.add(new User("4", "Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));

        groups = new ArrayList<>();
        groups.add(new Group());
        groups.add(new Group());
        groups.add(new Group());
        groups.add(new Group());

        jsonGroup = Json.createObjectBuilder().add("id", 1).build();

        authHeaders = new ArrayList<>();
        authHeaders.add(credentials);


        //Instantiate mocks
        initMocks(this);

        // Behaviour for the mocks
        initiateController();
        initiateHeaders();
        initiateDbHandler();
    }

    public void initiateDbHandler() {
        when(dbHandlerMock.getUser("1")).thenReturn(users.get(0));
        when(dbHandlerMock.getUser("2")).thenReturn(users.get(1));
        when(dbHandlerMock.getUser("3")).thenReturn(users.get(2));
        when(dbHandlerMock.getUser("4")).thenReturn(users.get(3));
    }

    public void initiateHeaders() {
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeaders);
    }

    public void initiateController() {
        when(controller.findGroup(1)).thenReturn(new Group());
        when(controller.findGroup(0)).thenReturn(null);

        when(controller.verify(credentials)).thenReturn(true);
        when(controller.verify(null)).thenReturn(false);
        when(controller.verify("")).thenReturn(false);
    }
}
