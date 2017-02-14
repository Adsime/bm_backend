package main.java.com.acc;

import com.acc.service.GroupService;
import com.acc.database.pojo.Group;
import com.acc.resources.GroupResource;
import main.java.com.acc.testResources.TestData;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.InternalServerErrorException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class GroupResourceTest {


    @Mock
    private GroupService controller;

    public GroupResource groupResource;

    @Before
    public void setup() {
        initMocks(this);
        this.groupResource = new GroupResource();
    }

    // Start getGroup tests

    @Test
    public void getGroupSuccessTest() {
        groupResource.service = controller;
        when(controller.verify(TestData.credentials)).thenReturn(true);
        when(controller.getGroup(1)).thenReturn(TestData.testGroups().get(0));
        assertEquals(HttpStatus.OK_200, groupResource.getGroup(1, TestData.testCredentials()).getStatus());
    }

    @Test
    public void getGroupAuthFailTest() {
        groupResource.service = controller;
        when(controller.verify(TestData.badCredentials)).thenReturn(false);
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.getGroup(1, TestData.testBadCredentials()).getStatus());
    }

    @Test
    public void getGroupNoEntryTest() {
        when(controller.getGroup(0)).thenReturn(null);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getGroup(0, TestData.testCredentials()).getStatus());
    }

    // End getGroup tests
    // Start getAllGroups Tests

    @Test
    public void getAllGroupsSuccessTest() {
        groupResource.service = controller;
        when(controller.getAllGroups()).thenReturn(TestData.testGroups());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        assertEquals(HttpStatus.OK_200, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    @Test
    public void getAllGroupsAuthFailTest() {
        groupResource.service = controller;
        when(controller.verify(TestData.badCredentials)).thenReturn(false);
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.getAllGroups(TestData.testBadCredentials()).getStatus());
    }

    @Test
    public void getAllGroupsSuccessInternalFailTest() {
        when(controller.getAllGroups()).thenThrow(new InternalServerErrorException());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    @Test
    public void getAllGroupsNoEntriesTest() {
        when(controller.getAllGroups()).thenReturn(new ArrayList<Group>());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    // End getAllGroups Tests
    // Start newGroup Tests

    @Test
    public void newGroupSuccessTest() {
        when(controller.newGroup(TestData.testGroups().get(0))).thenReturn(true);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.CREATED_201, groupResource.newGroup(TestData.jsonGroup(), TestData.testCredentials()).getStatus());
    }

    @Test
    public void newGroupAuthFailTest() {
        when(controller.verify(TestData.badCredentials)).thenReturn(false);
        groupResource.service = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.newGroup(TestData.jsonGroup(), TestData.testBadCredentials()).getStatus());
    }

    @Test
    public void newGroupFailTest() {
        when(controller.newGroup(any())).thenThrow(new InternalServerErrorException());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.newGroup(TestData.jsonGroup(), TestData.testCredentials()).getStatus());
    }

    // End newGroup Tests
    // Start deleteGroup Tests

    @Test
    public void deleteGroupSuccessTest() {
        groupResource.service = controller;
        when(controller.deleteGroup(0)).thenReturn(true);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        assertEquals(HttpStatus.NO_CONTENT_204, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    @Test
    public void deleteGroupAuthFailTest() {
        when(controller.verify(TestData.badCredentials)).thenReturn(false);
        groupResource.service = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.deleteGroup(0, TestData.testBadCredentials()).getStatus());
    }

    @Test
    public void deleteGroupNoEntryTest() {
        when(controller.deleteGroup(0)).thenReturn(false);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    @Test
    public void deleteGroupServerErrorTest() {
        when(controller.deleteGroup(0)).thenThrow(new InternalServerErrorException());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    // End deleteGroup Tests
    // Start updateGroup Tests

    @Test
    public void updateGroupSuccessTest() {
        when(controller.updateGroup(any())).thenReturn(true);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.OK_200, groupResource.updateGroup(0, TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    @Test
    public void updateGroupAuthFailTest() {
        when(controller.verify(TestData.badCredentials)).thenReturn(false);
        groupResource.service = controller;
        assertEquals(HttpStatus.UNAUTHORIZED_401, groupResource.updateGroup(0, TestData.testBadCredentials(), TestData.jsonGroup()).getStatus());
    }

    @Test
    public void updateGroupNoEntryTest() {
        when(controller.updateGroup(any())).thenReturn(false);
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.updateGroup(0, TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    @Test
    public void updateGroupServerFailTest() {
        when(controller.updateGroup(any())).thenThrow(new InternalServerErrorException());
        when(controller.verify(TestData.credentials)).thenReturn(true);
        groupResource.service = controller;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.updateGroup(0, TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    // End updateGroup Tests
}
































