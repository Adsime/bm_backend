package com.acc;

import com.acc.controller.GroupService;
import com.acc.resources.UserResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class UserResourceTest {

    @Mock
    private GroupService controller;

    public com.acc.resources.UserResource UserResource;

    @Before
    public void setup() {
        initMocks(this);
        UserResource = new UserResource();
    }

    // Start getUser Tests

    @Test
    public void getUserSuccess() {

    }

    @Test
    public void getUserAuthFail() {

    }

    @Test
    public void getUserNoEntries() {

    }

    @Test
    public void getUserInternalError() {

    }

    // End getUser Tests
    // Start getAllUsers Tests

    @Test
    public void getAllUsersSuccess() {

    }

    @Test
    public void getAllUsersAuthFail() {

    }

    @Test
    public void getAllUsersNoEntries() {

    }

    @Test
    public void getAllUsersInternalError() {

    }

    // End getAllUsers Tests
    // Start newUser Tests

    @Test
    public void newUsersSuccess() {

    }

    @Test
    public void newUsersAuthFail() {

    }

    @Test
    public void newUsersNoEntries() {

    }

    @Test
    public void newUsersInternalError() {

    }

    // End newUser Tests
    // Start updateUser Tests

    @Test
    public void updateUsersSuccess() {

    }

    @Test
    public void updateUsersAuthFail() {

    }

    @Test
    public void updateUsersNoEntries() {

    }

    @Test
    public void updateUsersInternalError() {

    }

    // End updateUser Tests
    // Start deleteUser Tests

    @Test
    public void deleteUsersSuccess() {

    }

    @Test
    public void deleteUsersAuthFail() {

    }

    @Test
    public void deleteUsersNoEntries() {

    }

    @Test
    public void deleteUsersInternalError() {

    }

    //End deleteUser Tests
}
