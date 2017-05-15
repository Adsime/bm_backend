package com.acc.database.repository;

import com.acc.database.entity.HbnPassword;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetPasswordByEIdSpec;
import com.acc.database.specification.GetUserByEIdSpec;
import com.acc.models.User;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

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

    @Override
    public User matchPassword(String username, String password) throws IllegalArgumentException {
        HbnPassword hbnPassword;
        HbnUser hbnUser;

        try {
            hbnUser = (HbnUser) super.queryToDb(new GetUserByEIdSpec(username)).get(0);
            String hashedEId = BCrypt.hashpw(username, hbnUser.getSalt());
            hbnPassword = (HbnPassword)super.queryToDb(new GetPasswordByEIdSpec(hashedEId)).get(0);

        } catch (EntityNotFoundException enf){
            throw new IllegalArgumentException("Feil i logg inn av Konto: \nBrukernavn eller passord stemmer ikke");
        }

        boolean match = BCrypt.checkpw(password, hbnPassword.getPassHash());
        if (match) return new User(
                (int)hbnUser.getId(),
                hbnUser.getFirstName(),
                hbnUser.getLastName(),
                hbnUser.getEmail(),
                hbnUser.getTelephone(),
                hbnUser.getEnterpriseId(),
                hbnUser.getAccessLevel(),
                hbnUser.getTags() != null ? super.toTagList(hbnUser.getTags()) : new ArrayList<>()
        );
        else {
            throw new IllegalArgumentException("Feil i logg inn av Konto: \nBrukernavn eller passord stemmer ikke");
        }
    }

    @Override
    public User register(String username, String password, User user) throws EntityNotFoundException, IllegalArgumentException{
        if(user.getFirstName().equals("") || user.getLastName().equals("") || user.getEmail().equals("") || user.getTelephone().equals("")){
            throw new IllegalArgumentException("Feil i registrering av gruppe: \nFyll ut alle nødvendige felter for bruker!");
        }
        HbnUser newAccountUser;
        try {
            newAccountUser = (HbnUser) super.queryToDb(new GetUserByEIdSpec(username)).get(0);
        } catch (EntityNotFoundException enfe){
            newAccountUser = null;
        }

        String salt = BCrypt.gensalt();
        String hashedEId = BCrypt.hashpw(username, salt);
        String hashedPassword = BCrypt.hashpw(password, salt);
        HbnPassword mappedPassword = new HbnPassword(hashedPassword,hashedEId);

        if (newAccountUser == null){
            HbnUser mappedUser = super.toHbnUser(user);

            try {
                if (user.getTags() != null) mappedUser.setTags(super.getHbnTagSet(user.getTags()));
            }catch (EntityNotFoundException enf){
                throw new EntityNotFoundException("Feil i registrering av bruker: \nEn eller flere merknader finnes ikke");
            }

            mappedUser.setSalt(salt);
            long id = super.addEntity(mappedUser);
            super.addEntity(mappedPassword);
            user.setId((int)id);
            return user;
        } else {
            newAccountUser.setSalt(salt);
            super.updateEntity(newAccountUser);
            super.addEntity(mappedPassword);
        }
        return user;
    }
}
