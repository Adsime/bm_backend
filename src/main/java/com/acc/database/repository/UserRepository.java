
package com.acc.database.repository;

import com.acc.Exceptions.MultipleChoiceException;
import com.acc.database.entity.*;
import com.acc.database.specification.GetPasswordByEIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.User;
import com.acc.providers.Links;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityNotFoundException;
import java.io.OptionalDataException;
import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

public class UserRepository extends AbstractRepository implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    // TODO: 10.05.2017 CHECK EID
    public User add(User user) throws EntityNotFoundException, IllegalArgumentException{
        if(user.getFirstName().equals("") || user.getLastName().equals("") || user.getEmail().equals("")){
            throw new IllegalArgumentException("Feil i registrering av bruker: \nFyll ut alle nødvendige felter! \n(Fornavn, Etternavn og E-Mail)");
        }

        HbnUser mappedUser = super.toHbnUser(user);

        try {
            if (user.getTags() != null) mappedUser.setTags(super.getHbnTagSet(user.getTags()));
        }catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Feil i registrering av bruker: \nEn eller flere merknader finnes ikke");
        }

        long id = super.addEntity(mappedUser);

        return new User(
                (int) id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTelephone(),
                user.getEnterpriseID(),
                user.getAccessLevel(),
                user.getTags()
        );
    }
    @Override
    public boolean update(User user) throws EntityNotFoundException {
        HbnUser mappedUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(user.getId())).get(0);
        String oldEId = mappedUser.getEnterpriseId();
        String salt = mappedUser.getSalt();
        mappedUser.setFirstName(user.getFirstName());
        mappedUser.setLastName(user.getLastName());
        mappedUser.setEmail(user.getEmail());
        mappedUser.setEnterpriseId(user.getEnterpriseID());

        try {
            if (user.getTags() != null) mappedUser.setTags(super.getHbnTagSet(user.getTags()));
        }catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Feil i oppdatering av bruker: \nEn eller flere merknader finnes ikke");
        }

        mappedUser.setId(user.getId());
        if(salt != null) updateUsername(oldEId, user.getEnterpriseID(), salt);

        try {
            return super.updateEntity(mappedUser);

        }catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Feil i oppdatering av bruker: \nBruker med id: " + user.getId() + " finnes ikke");
        }
    }

    public boolean remove(long id, boolean forced) throws MultipleChoiceException {
        HbnUser readUser;
        try {
            readUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(id)).get(0);
            if(!forced) {
                readUser.getGroups().forEach(group -> {
                    ArrayList<HbnUser> students = new ArrayList<>();
                    group.getUsers().forEach(user -> {
                        if(user.isStudent()) {
                            students.add(user);
                        }
                    });
                    if (students.size() < 2) {
                        StringBuilder builder = new StringBuilder()
                                .append(readUser.getFirstName())
                                .append(" ")
                                .append(readUser.getLastName())
                                .append(" er siste medlem i gruppen \"")
                                .append(group.getName())
                                .append("\".\nVed å slette brukeren slettes også brukeren.\n\nØnsker du å gjennomføre slettingen?");
                        throw new IllegalArgumentException(builder.toString());
                    }
                });
            }
            return remove(id);
        }catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Feil i sletting av bruker: \nBruker med id: " + id + " finnes ikke");
        }catch (IllegalArgumentException iae) {
            throw new MultipleChoiceException(iae.getMessage());
        }
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException{
        HbnUser readUser;
        try {
            readUser = (HbnUser) super.queryToDb(new GetUserByIdSpec(id)).get(0);
        }catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Feil i sletting av bruker: \nBruker med id: " + id + " finnes ikke");
        }

        if (readUser.getDocuments() != null){
            for (HbnDocument document : readUser.getDocuments()){
                document.setUser(null);

                try {
                    super.updateEntity(document);
                }catch (EntityNotFoundException ex){
                    throw new EntityNotFoundException("Feil i sletting av bruker: \nOppgave: \"" +  document.getTitle() + "\" , " + "til bruker finnes ikke");
                }
            }
        }
        if (readUser.getSalt() != null) {
            String hashedEId = BCrypt.hashpw(readUser.getEnterpriseId(), readUser.getSalt());
            HbnPassword hbnPassword = (HbnPassword) super.queryToDb(new GetPasswordByEIdSpec(hashedEId)).get(0);
            super.removeEntity(hbnPassword);
        }
        return super.removeEntity(readUser);
    }

    @Override
    public List<User> getQuery(Specification spec) throws EntityNotFoundException {
        List<HbnEntity> readData;
        try {
           readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Feil i henting av bruker: \nEn eller flere brukere finnes ikke!");
        }

        List<User> result =  new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnUser hbnUser = (HbnUser)entity;
            User user = super.toUser(hbnUser);

            List<String> pathList = new ArrayList<>();
            hbnUser.getDocuments().forEach(doc->pathList.add(doc.getPath()));
            user.setFiles(pathList);

            List<String> filePaths = new ArrayList<>();
            hbnUser.getDocuments().forEach(doc->filePaths.add(doc.getPath()));
            user.setFiles(filePaths);

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
        }catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Feil i henting av bruker: \nEn eller flere brukere finnes ikke!");
        }
        List<User> result =  new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnUser hbnUser = (HbnUser)entity;
            User user = super.toUser(hbnUser);
            if (!user.getTags().isEmpty()) user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG, user.getTagIdList()));
            if (hbnUser.getGroups() != null) user.addLinks(Links.GROUPS, Links.generateLinks(Links.GROUP, toGroupIdList(hbnUser.getGroups())));
            result.add(user);
        }
        return result;
    }

    private void updateUsername(String oldEID, String newEId, String salt){
        String oldHashedEId = BCrypt.hashpw(oldEID, salt);
        String newHashedEId = BCrypt.hashpw(newEId, salt);

        try{
            HbnPassword newHbnPassword = (HbnPassword) super.queryToDb(new GetPasswordByEIdSpec(oldHashedEId)).get(0);
            newHbnPassword.setEIdHash(newHashedEId);
            super.updateEntity(newHbnPassword);
        } catch (EntityNotFoundException enfe){
            throw new EntityNotFoundException("Feil i oppdatering av bruker: \nBruker med " + oldHashedEId + " finnes ikke!");
        }
    }
}
