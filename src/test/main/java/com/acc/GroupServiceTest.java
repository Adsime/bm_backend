package main.java.com.acc;

import com.acc.database.repository.GroupRepository;
import com.acc.models.Group;
import com.acc.service.GroupService;
import main.java.com.acc.testResources.TestData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ejb.NoSuchEntityException;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class GroupServiceTest {
    @Mock
    GroupRepository groupRepository;

    GroupService service;



    @Before
    public void setup() {
        initMocks(this);
        service = new GroupService();
        service.groupRepository = groupRepository;
    }

    //Start getGroup()

    /*@Test
    public void getGroupSuccessTest() {
        when(groupRepository.getQuery(any())).thenReturn(TestData.testGroups());
        Group expected = TestData.testGroups().get(0);
        Group actual = service.getGroup(0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getGroupNoEntryException() {
        when(groupRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Group expected = null;
        Group actual = service.getGroup(0);
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getGroupInternalError() {
        when(groupRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getGroup(0);
    }

    //End getGroup()
    //Start getAllGroups()

    @Test
    public void getAllGroupsSuccess() {
        when(groupRepository.getQuery(any())).thenReturn(TestData.testGroups());
        String expected = TestData.testGroups().toString();
        String actual = service.getAllGroups().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllGroupsEmptyList() {
        when(groupRepository.getQuery(any())).thenReturn(new ArrayList<Group>());
        boolean expected = service.getAllGroups().isEmpty();
        assertTrue(expected);
    }

    @Test
    public void getAllGroupsNoSuchEntity() {
        when(groupRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllGroups();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getAllGroupsInternalError() {
        when(groupRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllGroups();
    }

    //End getAllGroups()
    //Start newGroup()

    @Test
    public void newGroupSuccess() {
        when(groupRepository.add(any())).thenReturn(TestData.testGroups().get(0));
        String expected = TestData.testGroups().get(0).toString();
        String actual = service.newGroup(TestData.testGroups().get(0)).toString();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void newGroupFail() {
        when(groupRepository.add(any())).thenThrow(new InternalServerErrorException());
        service.newGroup(TestData.testGroups().get(0));
    }

    //End newGroup()
    //Start deleteGroup()

    @Test
    public void deleteGroupSuccess() {
        when(groupRepository.remove(0)).thenReturn(true);
        boolean acutal = service.deleteGroup(0);
        assertTrue(acutal);
    }

    @Test
    public void deleteGroupNoEntry() {
        when(groupRepository.remove(0)).thenReturn(false);
        boolean actual = service.deleteGroup(0);
        assertFalse(actual);
    }

    @Test
    public void deleteGroupNoEntryNull() {
        when(groupRepository.remove(0)).thenThrow(new NoSuchEntityException());
        boolean actual = service.deleteGroup(0);
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void deleteGroupInternalError() {
        when(groupRepository.remove(0)).thenThrow(new InternalServerErrorException());
        service.deleteGroup(0);
    }

    //End deleteGroup()
    //Start updateGroup()

    @Test
    public void updateGroupSuccess() {
        when(groupRepository.update(any())).thenReturn(true);
        boolean actual = service.updateGroup(TestData.testGroups().get(0));
        assertTrue(actual);
    }

    @Test
    public void updateGroupFail() {
        when(groupRepository.update(any())).thenReturn(false);
        boolean actual = service.updateGroup(TestData.testGroups().get(0));
        assertFalse(actual);
    }

    @Test
    public void updateGroupNoEntry() {
        when(groupRepository.update(any())).thenThrow(new NoSuchEntityException());
        boolean actual = service.updateGroup(TestData.testGroups().get(0));
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void updateGroupInternalError() {
        when(groupRepository.update(any())).thenThrow(new InternalServerErrorException());
        service.updateGroup(TestData.testGroups().get(0));
    }*/
}
