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
 * Created by nguyen.duy.j.khac on 28.03.2017.
 */
public class AccountRepositoryImpl extends AbstractRepository implements AccountRepository {

    private static Logger LOGGER = Logger.getLogger("application");

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
        if (match) return super.toUser(hbnUser);

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
