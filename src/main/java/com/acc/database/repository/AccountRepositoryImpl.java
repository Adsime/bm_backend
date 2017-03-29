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
 * Created by nguyen.duy.j.khac on 28.03.2017.
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

        try {
            super.queryToDb(new GetUserByEIdSpec(username));
        } catch (EntityNotFoundException enf){
            throw new IllegalArgumentException("Feil i registrering av Konto: \nEnterprise ID: " + username + " finnes fra før!");
        }

        String salt = BCrypt.gensalt();
        String hashedEId = BCrypt.hashpw(username, salt);
        String hashedPassword = BCrypt.hashpw(password, salt);
        HbnPassword mappedPassword = new HbnPassword(hashedPassword,hashedEId);

        super.addEntity(mappedPassword);

        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnterpriseID(),
                (user.getAccessLevel() == null) ? "0" : user.getAccessLevel()
        );

        try {
            if (user.getTags() != null) mappedUser.setTags(super.getHbnTagSet(user.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering av bruker: \nEn eller flere merknader finnes ikke");
        }

        mappedUser.setSalt(salt);

        long id = super.addEntity(mappedUser);

        return new User(
                (int) id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnterpriseID(),
                user.getAccessLevel(),
                user.getTags()
        );
    }
}