package com.acc.interfaces;

import com.acc.models.TagModel;
import com.acc.models.UserModel;

import java.util.List;

/**
 * Created by melsom.adrian on 27.01.2017.
 */
public interface UserService {
    List<UserModel> getUsers();
    UserModel getUser(String id);
    List<UserModel> getUserByTag(List<TagModel> tagModels);
}
