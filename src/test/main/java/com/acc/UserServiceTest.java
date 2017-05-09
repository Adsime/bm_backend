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
import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.InternalServerErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.booleanThat;
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

    //Start getContextUser()

    /*@Test
    public void getUserSuccessTest() {
        when(userRepository.getQuery(any())).thenReturn(TestData.testUsers());
        User expected = TestData.testUsers().get(0);
        User actual = service.getContextUser(0);
        assertEquals(expected.toString(), actual.toString());
    }*/

    /*@Test(expected = InternalServerErrorException.class)
    public void getUserInternalError() {
        when(userRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getContextUser(0);
    }
*/
    /*@Test
    public void getUserNoEntryException() {
        when(userRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        User expected = null;
        User actual = service.getContextUser(0);
        assertEquals(expected, actual);
    }*/

    //End getContextUser()
    //Start getAllUsers()

    /*@Test
    public void getAllUsersSuccess() {
        when(userRepository.getQuery(any())).thenReturn(TestData.testUsers());
        String expected = TestData.testUsers().toString();
        String actual = service.getAllUsers().toString();
        assertEquals(expected, actual);
    }*/

    /*@Test
    public void getAllUsersEmptyList() {
        when(userRepository.getQuery(any())).thenReturn(new ArrayList<User>());
        boolean expected = service.getAllUsers().isEmpty();
        assertTrue(expected);
    }*/

    /*@Test
    public void getAllUsersNoSuchEntity() {
        when(userRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllUsers();
        assertEquals(expected, actual);
    }*/

    /*@Test(expected = InternalServerErrorException.class)
    public void getAllUsersInternalError() {
        when(userRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllUsers();
    }*/

    //End getAllUsers()
    //Start newUser()

    /*@Test
    public void newUserSuccess() {
        when(userRepository.add(any())).thenReturn(TestData.testUsers().get(0));
        String expected = TestData.testUsers().get(0).toString();
        String actual = service.newUser(TestData.testUsers().get(0)).toString();
        assertEquals(expected, actual);
    }*/

    /*@Test(expected = InternalServerErrorException.class)
    public void newUserFail() {
        when(userRepository.add(any())).thenThrow(new InternalServerErrorException());
        service.newUser(TestData.testUsers().get(0));
    }*/

    //End newUser()
    //Start deleteUser()

    /*@Test
    public void deleteUserSuccess() {
        when(userRepository.remove(0)).thenReturn(true);
        boolean acutal = service.deleteUser(0);
        assertTrue(acutal);
    }*/

    /*@Test
    public void deleteUserNoEntry() {
        when(userRepository.remove(0)).thenReturn(false);
        boolean actual = service.deleteUser(0);
        assertFalse(actual);
    }*/

    /*@Test
    public void deleteUserNoEntryNull() {
        when(userRepository.remove(0)).thenThrow(new NoSuchEntityException());
        boolean actual = service.deleteUser(0);
        assertFalse(actual);
    }*/

   /* @Test(expected = InternalServerErrorException.class)
    public void deleteUserInternalError() {
        when(userRepository.remove(0)).thenThrow(new InternalServerErrorException());
        service.deleteUser(0);
    }*/

    //End deleteUser()
    //Start updateUser()

    /*@Test
    public void updateUserSuccess() {
        when(userRepository.update(any())).thenReturn(true);
        boolean actual = service.updateUser(TestData.testUsers().get(0));
        assertTrue(actual);
    }*/

    /*@Test
    public void updateUserFail() {
        when(userRepository.update(any())).thenReturn(false);
        boolean actual = service.updateUser(TestData.testUsers().get(0));
        assertFalse(actual);
    }*/

    /*@Test
    public void updateUserNoEntry() {
        when(userRepository.update(any())).thenThrow(new NoSuchEntityException());
        boolean actual = service.updateUser(TestData.testUsers().get(0));
        assertFalse(actual);
    }*/

    /*@Test(expected = InternalServerErrorException.class)
    public void updateUserInternalError() {
        when(userRepository.update(any())).thenThrow(new InternalServerErrorException());
        service.updateUser(TestData.testUsers().get(0));
    }*/
}















