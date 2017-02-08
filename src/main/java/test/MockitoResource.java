package test;

import com.acc.controller.Controller;
import com.acc.database.DbHandler;
import com.acc.models.User;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Before;
import org.mockito.Mock;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class MockitoResource {

    @Mock
    public DbHandler dbHandlerMock;
    @Mock
    public Controller controller;

    public Client client;
    public WebTarget target;
    HttpServer server;

    public String credentials;
    public List<User> users;

    @Before
    public void create() {
        users = new ArrayList<>();
        users.add(new User("1", "Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        users.add(new User("2", "Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        users.add(new User("3", "Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        users.add(new User("4", "Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));

        server = Main.startServer();
        credentials = "username:admin";
        client = ClientBuilder.newClient();
        target = client.target(Main.BASE_URI);
        
        initMocks(this);

        //DbHandler when-then
        when(dbHandlerMock.getUser("1")).thenReturn(users.get(0));
        when(dbHandlerMock.getUser("2")).thenReturn(users.get(1));
        when(dbHandlerMock.getUser("3")).thenReturn(users.get(2));
        when(dbHandlerMock.getUser("4")).thenReturn(users.get(3));

        //Controller when-then
        when(controller.verify("username:password")).thenReturn(true);
        when(controller.verify(null)).thenReturn(false);
        when(controller.verify("")).thenReturn(false);

    }
}
