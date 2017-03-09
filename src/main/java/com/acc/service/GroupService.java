package com.acc.service;

import com.acc.database.repository.GroupRepository;
import com.acc.database.specification.GetGroupAllSpec;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.models.Group;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

public class GroupService extends GeneralService{

    @Inject
    public GroupRepository groupRepository;

    public Group getGroup(int id) {
        try {
            return groupRepository.getQuery(new GetGroupByIdSpec((long)id)).get(0);
        } catch (NoSuchEntityException nsee) {
            return null;
        }
    }

    public List<Group> getAllGroups() {
        try {
            return groupRepository.getQuery(new GetGroupAllSpec());
        } catch (NoSuchEntityException nsee) {
            return Arrays.asList();
        }
    }

    public Group newGroup(Group group) {
        return groupRepository.add(group);
    }

    public boolean deleteGroup(int id) {
        try {
            return groupRepository.remove((long) id);
        } catch (NoSuchEntityException nsee) {
            return false;
        }
    }

    public boolean updateGroup(Group group) {
        try {
            return groupRepository.update(group);
        } catch (NoSuchEntityException nsee) {
            return false;
        }
    }
}