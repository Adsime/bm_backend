package test.database;

import com.acc.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class DbHandler {

    private List<User> users = new ArrayList<>();

    public DbHandler() {
        users.add(new User("1", "Adrian", "Melsom", "melsom.adrian", "adrianmelsom@gmail.com"));
        users.add(new User("2", "Vu", "Melsom", "vu.kim", "kimvu@gmail.com"));
        users.add(new User("3", "Håkon", "Smørvik", "smørvik.håkon", "håkonsmørvik@gmail.com"));
        users.add(new User("4", "Duy", "Nguyen", "nguyen.duy", "duynguyen@gmail.com"));


    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String id) {
        for(User u : users) {
            if(u.getId().equals(id)) return u;
        }
        return null;
    }


}
