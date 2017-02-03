package test;

import com.acc.database.DbHandler;
import com.acc.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class UserControllerMockTest {

    @Mock
    public DbHandler dbHandlerMock;

    public List<User> users;

    @Before
    public void create() {
        users = new ArrayList<>();
        users.add(new User("1", "Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        users.add(new User("2", "Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        users.add(new User("3", "Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        users.add(new User("4", "Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));
        initMocks(this);
        when(dbHandlerMock.getUser("1")).thenReturn(users.get(1));
        when(dbHandlerMock.getUser("2")).thenReturn(users.get(2));
        when(dbHandlerMock.getUser("3")).thenReturn(users.get(3));
        when(dbHandlerMock.getUser("4")).thenReturn(users.get(4));
    }
}
