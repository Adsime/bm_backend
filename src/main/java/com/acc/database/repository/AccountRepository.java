package com.acc.database.repository;

import com.acc.models.User;

/**
 * Created by nguyen.duy.j.khac on 28.03.2017.
 */
public interface AccountRepository {
    User matchPassword(String username, String password);
    User register(String username, String password, User user);
    User getAccount(String username);
}
