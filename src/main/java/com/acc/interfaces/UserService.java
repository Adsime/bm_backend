package com.acc.interfaces;

import com.acc.models.Tag;
import com.acc.models.User;

import java.util.List;

/**
 * Created by melsom.adrian on 27.01.2017.
 */
public interface UserService {
    List<User> getUsers();
    User getUser(String id);
    List<User> getUserByTag(List<Tag> tags);
}
