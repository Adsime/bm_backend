
package com.acc.database.repository;

import com.acc.database.entity.*;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.User;
import com.acc.providers.Links;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

public class UserRepository extends AbstractRepository implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    public User add(User user) throws EntityNotFoundException{
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

        //mappedUser.setSalt(BCrypt.gensalt());

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
    @Override
    public boolean update(User user) throws EntityNotFoundException {
        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnterpriseID(),
                user.getAccessLevel()
        );

        try {
            if (user.getTags() != null) mappedUser.setTags(super.getHbnTagSet(user.getTags()));
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av bruker: \nEn eller flere merknader finnes ikke");
        }

        mappedUser.setId(user.getId());

        try {
            return super.updateEntity(mappedUser);

        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i oppdatering av bruker: \nBruker med id: " + user.getId() + " finnes ikke");
        }
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException{
        HbnUser readUser;
        try {
            readUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(id)).get(0);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av bruker: \nBruker med id: " + id + " finnes ikke");
        }

        if (readUser.getProblems() != null){
            for (HbnProblem problem : readUser.getProblems()){
                problem.setUser(null);

                try {
                    super.updateEntity(problem);
                }catch (EntityNotFoundException enf){
                    throw new EntityNotFoundException("Feil i sletting av bruker: \nOppgave: \"" +  problem.getTitle() + "\" , " + "til bruker finnes ikke");
                }
            }
        }
        return super.removeEntity(readUser);
    }

    @Override
    public List<User> getQuery(Specification spec) throws EntityNotFoundException {
        List<HbnEntity> readData;
        try {
           readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf) {
            throw new EntityNotFoundException("Feil i henting av bruker: \nEn eller flere brukere finnes ikke!");
        }

        List<User> result =  new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnUser hbnUser = (HbnUser)entity;

            User user = new User(
                    (int)hbnUser.getId(),
                    hbnUser.getFirstName(),
                    hbnUser.getLastName(),
                    hbnUser.getEmail(),
                    hbnUser.getEnterpriseId(),
                    hbnUser.getAccessLevel(),
                    hbnUser.getTags() != null ? super.toTagList(hbnUser.getTags()) : new ArrayList<>()
            );

           if (!user.getTags().isEmpty()) user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
           if (hbnUser.getGroups() != null) user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, toGroupIdList(hbnUser.getGroups())));
            result.add(user);
        }
        return result;
    }

    @Override
    public List<User> getMinimalQuery(Specification spec) throws EntityNotFoundException {
        List<HbnEntity> readData;
        try {
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf) {
            throw new EntityNotFoundException("Feil i henting av bruker: \nEn eller flere brukere finnes ikke!");
        }
        List<User> result =  new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnUser hbnUser = (HbnUser)entity;

            User user = new User();
            user.setId((int)hbnUser.getId());
            user.setFirstName(hbnUser.getFirstName());
            user.setFirstName(hbnUser.getLastName());
            user.setTags(super.toTagList(hbnUser.getTags()));

            if (!user.getTags().isEmpty()) user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
            if (hbnUser.getGroups() != null) user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, toGroupIdList(hbnUser.getGroups())));
            result.add(user);
        }
        return result;
    }
}
