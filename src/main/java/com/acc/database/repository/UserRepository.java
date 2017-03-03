package com.acc.database.repository;

import com.acc.database.pojo.*;
import com.acc.database.specification.GetTagByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.providers.Links;
import com.sun.xml.internal.bind.v2.TODO;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 01.03.2017 Return User instead of boolean
public class UserRepository extends AbstractRepository<HbnUser> implements Repository<User> {

    public UserRepository(){
        super();
    }

    @Override
    public User add(User user) throws EntityNotFoundException{

        Set<HbnTag> tags = super.toHbnTagSet(user.getTags());

        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "",
                user.getEnterpriseID()
        );

        mappedUser.setTags(tags);
        long id = super.addEntity(mappedUser);

        return new User(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnterpriseID(),
                user.getTags());
    }

    // TODO: 24.02.2017 generate salt 
    @Override
    public boolean update(User user) {
        HbnUser mappedUser = new HbnUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "",
                user.getEnterpriseID()
        );

        if (!user.getTags().isEmpty()) mappedUser.setTags(getHbnUserTags(user));
        mappedUser.setId(user.getId());
        return super.updateEntity(mappedUser);
    }

    @Override
    public boolean remove(long id) {
        HbnUser mappedUser = new HbnUser();
        mappedUser.setId(id);
        return super.removeEntity(mappedUser);
    }

    @Override
    public List<User> getQuery(Specification spec) {
        List<HbnUser> readUsers = super.queryFromDb((HqlSpecification) spec);
        List<User> result = new ArrayList<>();

        System.out.println(Arrays.toString(readUsers
                .get(0)
                .getTags()
                .toArray()));

        System.out.println(Arrays.toString(readUsers
                .get(0)
                .getGroups()
                .toArray()));


        for (HbnUser readUser : readUsers){
            User user = new User(
                    readUser.getId(),
                    readUser.getFirstName(),
                    readUser.getLastName(),
                    readUser.getEmail(),
                    readUser.getEnterpriseId(),
                    mapTagToHbnTag(readUser.getTags())
            );

            user.addLinks(Links.TAGS,Links.generateLinks(Links.TAG,toTagIdList(readUser.getTags())));
            user.addLinks(Links.GROUPS,Links.generateLinks(Links.GROUP,toGroupIdList(readUser.getGroups())));
            result.add(user);
        }

        return result;
    }

    private Set<HbnTag> getHbnUserTags (User user){
        Set<HbnTag> tagSet = new HashSet<>();

        for (Tag tag : user.getTags()){
            tagSet.add(new HbnTag(
                    tag.getName(),
                    tag.getDescription(),
                    tag.getType()
            ));
        }

        return tagSet;
    }

    private List<Tag> mapTagToHbnTag(Set<HbnTag> tagSet){
        List<Tag> tagList = new ArrayList<>();

        for (HbnTag hbnTag : tagSet){
            tagList.add(new Tag(
                    (int) hbnTag.getId(),
                    hbnTag.getTagName(),
                    hbnTag.getType(),
                    hbnTag.getDescription()
            ));
        }

        return tagList;
    }

    public List<Integer> toGroupIdList(Set<HbnBachelorGroup> hbnBachelorGroupSet){
        List<Integer> groupIdList = new ArrayList<>();

        for(HbnBachelorGroup hbnBachelorGroup : hbnBachelorGroupSet){
            groupIdList.add((int) hbnBachelorGroup.getId());
        }

        return groupIdList;
    }

    public List<Integer> toTagIdList(Set<HbnTag> hbnTagSet){
        List<Integer> tagIdList = new ArrayList<>();

        for(HbnTag hbnTag : hbnTagSet){
            tagIdList.add((int)hbnTag.getId());
        }

        return tagIdList;
    }
}
