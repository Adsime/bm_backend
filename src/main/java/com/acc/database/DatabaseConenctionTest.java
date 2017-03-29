package com.acc.database;

import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.ProblemRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetGroupByIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.Group;
import com.acc.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    public static void main(String[] args) {
        UserRepository UR = new UserRepository();
        TagRepository TR = new TagRepository();
        GroupRepository GR = new GroupRepository();
        ProblemRepository PR = new ProblemRepository();

        Group group11 = GR.getQuery(new GetGroupByIdSpec(2)).get(0);
        System.out.println("Group: " + group11.getName() + "\n---------------------------");
        for(User students : group11.getStudents()){
            System.out.println(students.getId() + ": " +students.getFirstName() + " " + students.getLastName() + " , ");
        }
        for(User supervisors : group11.getSupervisors()){
            System.out.println(supervisors.getId() + ": " + supervisors.getFirstName() + " " + supervisors.getLastName() + " , ");
        }

        User hakon = UR.getQuery(new GetUserByIdSpec(6)).get(0);
        hakon.setFirstName("Hochfrau");

        List<User> newStudents = new ArrayList<>();
        newStudents.add(hakon);
        group11.setSupervisors(newStudents);
        GR.update(group11);

        group11 = GR.getQuery(new GetGroupByIdSpec(2)).get(0);
        System.out.println("Group: " + group11.getName() + "\n---------------------------");
        for(User students : group11.getStudents()){
            System.out.println(students.getId() + ": " +students.getFirstName() + " " + students.getLastName() + " , ");
        }
        for(User supervisors : group11.getSupervisors()){
            System.out.println(supervisors.getId() + ": " + supervisors.getFirstName() + " " + supervisors.getLastName() + " , ");
        }
    }
}
