package main.java.com.acc;

import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.User;
import com.acc.service.UserService;
import main.java.com.acc.testResources.TestData;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService service;



    @Before
    public void setup() {
        initMocks(this);
        service = new UserService();
        service.userRepository = userRepository;
    }

    //Start getUser()

    @Test
    public void getUserSuccessTest() {
        when(userRepository.getQuery(any())).thenReturn(TestData.testUsers());
        User expected = TestData.testUsers().get(0);
        User actual = service.getUser(0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getUserNoEntryException() {
        when(userRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        User expected = null;
        User actual = service.getUser(0);
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getUserInternalError() {
        when(userRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getUser(0);
    }

    //End getUser()
    //Start getAllUsers()

    @Test
    public void getAllUsersSuccess() {
        when(userRepository.getQuery(any())).thenReturn(TestData.testUsers());
        String expected = TestData.testUsers().toString();
        String actual = service.getAllUsers().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersEmptyList() {
        when(userRepository.getQuery(any())).thenReturn(new ArrayList<User>());
        boolean expected = service.getAllUsers().isEmpty();
        assertTrue(expected);
    }

    @Test
    public void getAllUsersNoSuchEntity() {
        when(userRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllUsers();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getAllUsersInternalError() {
        when(userRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllUsers();
    }
}
