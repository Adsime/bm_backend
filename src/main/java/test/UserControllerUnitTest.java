package test.resources;


import com.acc.controller.UserController;
import org.junit.Test;
import test.database.DbHandler;

import static org.junit.Assert.assertEquals;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class UserControllerUnitTest {

    private DbHandler db = new DbHandler();
    private UserController uc = new UserController();

    @Test
    public void testGet() {
        assertEquals(true, true);
    }
}
