package test;

import com.acc.resources.GroupResource;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class GroupResourceTest extends MockitoResource {

    /**
     * There are some global variables which should be used in all test cases.
     * These are defined in the class MockitoResource. It contains certain mocked
     * classes and constants like credentials. The variable used to do requests
     * are global as well.
     *
     * @String credentials : contains the credentials used in testing.
     * @Controller controller : a mocked controller.
     */

    @Test
    public void getGroupSuccessTest() {
        GroupResource groupResource = new GroupResource();
        groupResource.controller = controller;
        assertEquals(HttpStatus.OK_200, groupResource.getGroup(1, headers).getStatus());
    }

    @Test
    public void getGroupAuthFailTest() {
        GroupResource groupResource = new GroupResource();
        groupResource.controller = controller;
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        assertEquals(HttpStatus.FORBIDDEN_403, groupResource.getGroup(1, headers).getStatus());
    }

    @Test
    public void getGroupNotFoundTest() {
        GroupResource groupResource = new GroupResource();
        groupResource.controller = controller;
        assertEquals(HttpStatus.NOT_FOUND_404, groupResource.getGroup(0, headers).getStatus());
    }


}
