package main.java.com.acc;

import com.acc.models.Group;
import com.acc.models.Problem;
import com.acc.service.GeneralService;
import com.acc.service.GroupService;
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
    private GroupService service;

    public GroupResource groupResource;

    @Before
    public void setup() {
        initMocks(this);
        this.groupResource = new GroupResource();
    }

    // Start getGroup tests

    @Test
    public void getGroupSuccessTest() {
        GeneralService gs = new GeneralService();
        groupResource.service = service;
        when(service.getGroup(1)).thenReturn(TestData.testGroups().get(0));
        assertEquals(HttpStatus.OK_200, groupResource.getGroup(1, TestData.testCredentials()).getStatus());
    }

    @Test
    public void getGroupNoEntryTest() {
        when(service.getGroup(0)).thenReturn(null);
        groupResource.service = service;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getGroup(0, TestData.testCredentials()).getStatus());
    }

    // End getGroup tests
    // Start getAllGroups Tests

    @Test
    public void getAllGroupsSuccessTest() {
        groupResource.service = service;
        when(service.getAllGroups()).thenReturn(TestData.testGroups());
        assertEquals(HttpStatus.OK_200, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    @Test
    public void getAllGroupsSuccessInternalFailTest() {
        when(service.getAllGroups()).thenThrow(new InternalServerErrorException());
        groupResource.service = service;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    @Test
    public void getAllGroupsNoEntriesTest() {
        when(service.getAllGroups()).thenReturn(new ArrayList<Group>());
        groupResource.service = service;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.getAllGroups(TestData.testCredentials()).getStatus());
    }

    // End getAllGroups Tests
    // Start newGroup Tests

    @Test
    public void newGroupSuccessTest() {
        when(service.newGroup(any())).thenReturn(TestData.testGroups().get(0));
        groupResource.service = service;
        assertEquals(HttpStatus.CREATED_201, groupResource.newGroup(TestData.jsonGroup(), TestData.testCredentials()).getStatus());
    }

    @Test
    public void newGroupFailTest() {
        when(service.newGroup(any())).thenThrow(new InternalServerErrorException());
        groupResource.service = service;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.newGroup(TestData.jsonGroup(), TestData.testCredentials()).getStatus());
    }

    // End newGroup Tests
    // Start deleteGroup Tests

    @Test
    public void deleteGroupSuccessTest() {
        groupResource.service = service;
        when(service.deleteGroup(0)).thenReturn(true);
        assertEquals(HttpStatus.NO_CONTENT_204, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    @Test
    public void deleteGroupNoEntryTest() {
        when(service.deleteGroup(0)).thenReturn(false);
        groupResource.service = service;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    @Test
    public void deleteGroupServerErrorTest() {
        when(service.deleteGroup(0)).thenThrow(new InternalServerErrorException());
        groupResource.service = service;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.deleteGroup(0, TestData.testCredentials()).getStatus());
    }

    // End deleteGroup Tests
    // Start updateGroup Tests

    @Test
    public void updateGroupSuccessTest() {
        when(service.updateGroup(any())).thenReturn(true);
        groupResource.service = service;
        assertEquals(HttpStatus.OK_200, groupResource.updateGroup(TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    @Test
    public void updateGroupNoEntryTest() {
        when(service.updateGroup(any())).thenReturn(false);
        groupResource.service = service;
        assertEquals(HttpStatus.BAD_REQUEST_400, groupResource.updateGroup(TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    @Test
    public void updateGroupServerFailTest() {
        when(service.updateGroup(any())).thenThrow(new InternalServerErrorException());
        groupResource.service = service;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, groupResource.updateGroup(TestData.testCredentials(), TestData.jsonGroup()).getStatus());
    }

    // End updateGroup Tests
}
































