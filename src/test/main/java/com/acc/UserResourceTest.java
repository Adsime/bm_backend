package main.java.com.acc;

import com.acc.models.User;
import com.acc.service.UserService;
import com.acc.resources.UserResource;
import main.java.com.acc.testResources.TestData;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class UserResourceTest {

    @Mock
    private UserService service;

    public UserResource userResource;

    int expected, actual;
    @Before
    public void setup() {
        initMocks(this);
        userResource = new UserResource();
    }

    // Start getUser Tests

    @Test
    public void getUserSuccess() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getUser(0)).thenReturn(TestData.testUsers().get(0));
        expected = HttpStatus.OK_200;
        actual = userResource.getUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserAuthFail() {
        userResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.getUser(0)).thenReturn(TestData.testUsers().get(0));
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = userResource.getUser(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserNoEntries() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getUser(0)).thenReturn(null);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = userResource.getUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserInternalError() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getUser(0)).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = userResource.getUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getUser Tests
    // Start getAllUsers Tests

    @Test
    public void getAllUsersSuccess() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        try {
            when(service.getAllUsers()).thenReturn(TestData.testUsers());
        } catch (Exception e) {

        }
        expected = HttpStatus.OK_200;
        actual = userResource.getAllUsers(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersAuthFail() {
        userResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        try {
            when(service.getAllUsers()).thenReturn(TestData.testUsers());
        } catch (Exception e) {

        }
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = userResource.getAllUsers(TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersNoEntries() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        try {
            when(service.getAllUsers()).thenReturn(new ArrayList<User>());
        } catch (Exception e) {

        }

        expected = HttpStatus.BAD_REQUEST_400;
        actual = userResource.getAllUsers(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersInternalError() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        try {
            when(service.getAllUsers()).thenThrow(new InternalServerErrorException());
        } catch (Exception e) {

        }

        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = userResource.getAllUsers(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getAllUsers Tests
    // Start newUser Tests

    @Test
    public void newUsersSuccess() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        try {
            when(service.newUser(any())).thenReturn(true);
        } catch (Exception e) {

        }

        expected = HttpStatus.CREATED_201;
        actual = userResource.newUser(TestData.jsonUser(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newUsersAuthFail() {
        userResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        try {
            when(service.newUser(any())).thenReturn(true);
        } catch (Exception e) {

        }
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = userResource.newUser(TestData.jsonUser(), TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newUsersInternalError() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        try {
            when(service.newUser(any())).thenThrow(new InternalServerErrorException());
        } catch (Exception e) {

        }

        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = userResource.newUser(TestData.jsonUser(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End newUser Tests
    // Start updateUser Tests

    @Test
    public void updateUsersSuccess() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateUser(any())).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = userResource.updateUser(TestData.jsonUser(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateUsersAuthFail() {
        userResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.updateUser(any())).thenReturn(true);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = userResource.updateUser(TestData.jsonUser(), TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateUsersNoEntries() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateUser(any())).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = userResource.updateUser(TestData.jsonUser(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateUsersInternalError() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateUser(any())).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = userResource.updateUser(TestData.jsonUser(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End updateUser Tests
    // Start deleteUser Tests

    @Test
    public void deleteUsersSuccess() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteUser(0)).thenReturn(true);
        expected = HttpStatus.NO_CONTENT_204;
        actual = userResource.deleteUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUsersAuthFail() {
        userResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.deleteUser(0)).thenReturn(true);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = userResource.deleteUser(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUsersNoEntries() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteUser(0)).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = userResource.deleteUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUsersInternalError() {
        userResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteUser(0)).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = userResource.deleteUser(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    //End deleteUser Tests
}
