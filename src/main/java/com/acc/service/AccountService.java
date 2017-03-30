package com.acc.service;

import com.acc.database.repository.AccountRepositoryImpl;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserByEIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.Credentials;
import com.acc.models.User;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
public class AccountService {

    @Inject
    private AccountRepositoryImpl repo;

    @Inject
    private TokenHandler tokenHandler;

    @Inject
    private UserRepository userRepository;

    public User verifyUser(String encodedCreds) {
        try {
            Credentials credentials = new Credentials(encodedCreds);
            User user = repo.matchPassword(credentials.getUsername(), credentials.getPassword());
            return user;
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (EntityNotFoundException enfe) {
            enfe.printStackTrace();
        }
        return null;
    }

    public String getToken(User user) {
        return tokenHandler.generateToken(user);
    }

    public User deleteThis(int id, String pw) {
        User user = userRepository.getQuery(new GetUserByIdSpec(id)).get(0);
        return repo.register(user.getEnterpriseID(), pw, user);
    }
}
