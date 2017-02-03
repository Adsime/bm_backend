package test;

import com.acc.database.DbHandler;
import com.acc.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class GroupResourceTest extends MockitoResource {

    @Test
    public void test() {
        User user = dbHandlerMock.getUser("2");
        System.out.println(user);

    }
}
