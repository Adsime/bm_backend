package com.acc.database.repository;

import com.acc.database.entity.HbnPassword;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetPasswordByEIdSpec;
import com.acc.database.specification.GetUserByEIdSpec;
import com.acc.models.User;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityNotFoundException;

/**
 * Created by nguyen.duy.j.khac on 28.03.2017
 *
 * jBCrypt
 * Copyright (c) 2006 Damien Miller <djm@mindrot.org>
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

public class AccountRepositoryImpl extends AbstractRepository implements AccountRepository {

    private static Logger LOGGER = Logger.getLogger("application");

    @Override
    public User matchPassword(String username, String password) throws IllegalArgumentException {
        HbnPassword hbnPassword;
        HbnUser hbnUser;

        try {
            hbnUser = (HbnUser) super.queryToDb(new GetUserByEIdSpec(username)).get(0);
            if(hbnUser.getSalt() == null) {
                throw new EntityNotFoundException("Brukeren har ikke salt");
            }
            String hashedEId = BCrypt.hashpw(username, hbnUser.getSalt());
            hbnPassword = (HbnPassword)super.queryToDb(new GetPasswordByEIdSpec(hashedEId)).get(0);

        } catch (EntityNotFoundException enf){
            throw new IllegalArgumentException("Feil i logg inn av Konto: \nBrukernavn eller passord stemmer ikke");
        }

        boolean match = BCrypt.checkpw(password, hbnPassword.getPassHash());
        if (match) return super.toUser(hbnUser);

        else {
            throw new IllegalArgumentException("Feil i logg inn av Konto: \nBrukernavn eller passord stemmer ikke");
        }
    }

    @Override
    public User register(String username, String password, User user) throws EntityNotFoundException, IllegalArgumentException{
        if(user.getFirstName().equals("") || user.getLastName().equals("") || user.getEmail().equals("") || user.getTelephone().equals("")){
            throw new IllegalArgumentException("Feil i registrering av konto: \nFyll ut alle nødvendige felter for bruker!");
        }

        HbnUser newAccountUser;
        try {
            newAccountUser = (HbnUser) super.queryToDb(new GetUserByEIdSpec(username)).get(0);
        } catch (EntityNotFoundException enfe){
            throw new IllegalArgumentException("Feil i registrering av konto: \nBruker med id " + user.getId() + " finnes ikke!");
        }
        if(newAccountUser.getSalt() != null) {
            HbnPassword hbnPassword;
            String heid = BCrypt.hashpw(newAccountUser.getEnterpriseId(), newAccountUser.getSalt());
            try {
                hbnPassword = (HbnPassword)super.queryToDb(new GetPasswordByEIdSpec(heid)).get(0);
            } catch (EntityNotFoundException enfe) {
                hbnPassword = null;
                LOGGER.info("Account for user " + username + " in creation");
            }
            if(hbnPassword != null) {
                throw new IllegalArgumentException("User already has an account");
            }
        }

        String salt = BCrypt.gensalt();
        String hashedEId = BCrypt.hashpw(username, salt);
        String hashedPassword = BCrypt.hashpw(password, salt);
        HbnPassword mappedPassword = new HbnPassword(hashedPassword,hashedEId);

        newAccountUser.setSalt(salt);
        newAccountUser.setAccessLevel(
                user.getAccessLevel() == null || user.getAccessLevel().equals("0") ? "1" : user.getAccessLevel()
        );

        super.updateEntity(newAccountUser);
        super.addEntity(mappedPassword);
        return user;
    }

    @Override
    public User getAccount(String username) {
        try {
            HbnUser accountUser = (HbnUser) super.queryToDb(new GetUserByEIdSpec(username)).get(0);

            if(accountUser.getSalt() == null) {
                return null;
            }

            String hashedUN = BCrypt.hashpw(username, accountUser.getSalt());
            if(super.queryToDb(new GetPasswordByEIdSpec(hashedUN)).get(0) != null) {
                return super.toUser(accountUser);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to get user with username " + username + " from the database.", e);
        }
        return null;
    }
}
