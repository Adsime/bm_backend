package test;

import com.acc.database.DbHandler;
import com.acc.models.UserModel;
import org.junit.Before;
import org.mockito.Mock;

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

    public List<UserModel> userModels;

    @Before
    public void create() {
        userModels = new ArrayList<>();
        userModels.add(new UserModel("1", "Adrian", "Melsom", "melsom.adrian", "melsom.adrian@accenture.com"));
        userModels.add(new UserModel("2", "Duy", "Nguyen", "nguyen.duy", "nguyen.duy@accenture.com"));
        userModels.add(new UserModel("3", "Håkon", "Smørvik", "smørvik.håkon", "smørvik.håkon@accenture.com"));
        userModels.add(new UserModel("4", "Kim", "Vu", "vu.kim", "vu.kim@accenture.com"));
        initMocks(this);
        when(dbHandlerMock.getUser("1")).thenReturn(userModels.get(1));
        when(dbHandlerMock.getUser("2")).thenReturn(userModels.get(2));
        when(dbHandlerMock.getUser("3")).thenReturn(userModels.get(3));
        when(dbHandlerMock.getUser("4")).thenReturn(userModels.get(4));
    }
}
