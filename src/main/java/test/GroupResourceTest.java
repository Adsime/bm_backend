package test;

import com.acc.models.Group;
import com.acc.resources.GroupResource;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.*;

import static org.mockito.Mockito.when;
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

    public GroupResource groupResource;

    @Before
    public void setup() {
        this.groupResource = new GroupResource();
    }

    // Start getGroup tests

    @Test
    public void getGroupSuccessTest() {
        groupResource.controller = controller;
        assertEquals(HttpStatus.OK_200, groupResource.getGroup(1, headers).getStatus());
    }

    @Test
    public void getGroupAuthFailTest() {
        groupResource.controller = controller;
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.getGroup(1, headers).getStatus());
    }

    @Test
    public void getGroupNoEntryTest() {
        when(controller.findGroup(0)).thenReturn(null);
        groupResource.controller = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getGroup(0, headers).getStatus());
    }

    // End getGroup tests
    // Start getAllGroups Tests

    @Test
    public void getAllGroupsSuccessTest() {
        groupResource.controller = controller;
        when(controller.findAllGroups()).thenReturn(groups);
        assertEquals(HttpStatus.OK_200, groupResource.getAllGroups(headers).getStatus());
    }

    @Test
    public void getAllGroupsAuthFailTest() {
        groupResource.controller = controller;
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.getAllGroups(headers).getStatus());
    }

    @Test
    public void getAllGroupsSuccessInternalFailTest() {
        when(controller.findAllGroups()).thenThrow(new InternalServerErrorException());
        groupResource.controller = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.getAllGroups(headers).getStatus());
    }

    @Test
    public void getAllGroupsNoEntriesTest() {
        when(controller.findAllGroups()).thenReturn(new ArrayList<Group>());
        groupResource.controller = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getAllGroups(headers).getStatus());
    }

    // End getAllGroups Tests
    // Start newGroup Tests

    @Test
    public void newGroupSuccessTest() {
        when(controller.createNewGroup(new Group())).thenReturn(true);
        groupResource.controller = controller;
        assertEquals(HttpStatus.CREATED_201, groupResource.newGroup(jsonGroup, headers).getStatus());
    }

    @Test
    public void newGroupAuthFailTest() {
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        groupResource.controller = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.newGroup(jsonGroup, headers).getStatus());
    }

    @Test
    public void newGroupFailTest() {
        when(controller.createNewGroup(new Group())).thenThrow(new InternalServerErrorException());
        groupResource.controller = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.newGroup(jsonGroup, headers).getStatus());
    }

    // End newGroup Tests
    // Start deleteGroup Tests

    @Test
    public void deleteGroupSuccessTest() {
        groupResource.controller = controller;
        when(controller.deleteGroup(0)).thenReturn(true);
        assertEquals(HttpStatus.NO_CONTENT_204, groupResource.deleteGroup(0, headers).getStatus());
    }

    @Test
    public void deleteGroupAuthFailTest() {
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        groupResource.controller = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.deleteGroup(0, headers).getStatus());
    }

    @Test
    public void deleteGroupNoEntryTest() {
        when(controller.deleteGroup(0)).thenReturn(false);
        groupResource.controller = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.deleteGroup(0, headers).getStatus());
    }

    @Test
    public void deleteGroupServerErrorTest() {
        when(controller.deleteGroup(0)).thenThrow(new InternalServerErrorException());
        groupResource.controller = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.deleteGroup(0, headers).getStatus());
    }

    // End deleteGroup Tests
    // Start updateGroup Tests

    @Test
    public void updateGroupSuccessTest() {
        when(controller.updateGroup(new Group())).thenReturn(true);
        groupResource.controller = controller;
        assertEquals(HttpStatus.OK_200, groupResource.updateGroup(0, headers, jsonGroup).getStatus());
    }

    @Test
    public void updateGroupAuthFailTest() {
        authHeaders = new ArrayList<>();
        authHeaders.add(badCredentials);
        initiateHeaders();
        groupResource.controller = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.updateGroup(0, headers, jsonGroup).getStatus());
    }

    @Test
    public void updateGroupNoEntryTest() {
        when(controller.updateGroup(new Group())).thenReturn(false);
        groupResource.controller = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.updateGroup(0, headers, jsonGroup).getStatus());
    }

    @Test
    public void updateGroupServerFailTest() {
        when(controller.updateGroup(new Group())).thenThrow(new InternalServerErrorException());
        groupResource.controller = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.updateGroup(0, headers, jsonGroup).getStatus());
    }

    // End updateGroup Tests
}
































