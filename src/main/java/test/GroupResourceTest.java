package test;

import com.acc.controller.Controller;
import com.acc.database.DbHandler;
import com.acc.models.Group;
import com.acc.models.User;
import com.acc.resources.GroupResource;
import com.sun.net.httpserver.HttpServer;
import com.sun.prism.GraphicsPipeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class GroupResourceTest extends MockitoResource {

    private GroupResource groupResource;
    private WebTarget target;
    private HttpServer server;

    @Before
    public void create() {
        System.out.println(server.toString());


    }

    @Test
    public void getGroupSuccessTest() {

    }

    @Test
    public void getGroupAuthFailTest() {

    }

    @Test
    public void getGroupNotFoundTest() {

    }

    @After
    public void tearDown() throws Exception {
        server.stop(0);
    }
}
