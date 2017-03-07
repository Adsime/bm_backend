package com.acc.service;

import com.acc.database.repository.GroupRepository;
import com.acc.models.Group;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

public class GroupService extends GeneralService{

    @Inject
    public GroupRepository groupRepository;

    public Group getGroup(int id) {
        return null;
    }

    public List<Group> getAllGroups() {
        return null;
    }

    public Group newGroup(Group group) {
        return groupRepository.add(group);
    }

    public boolean deleteGroup(int id) {
        return true;
    }

    public boolean updateGroup(Group group) {
        return true;
    }
}