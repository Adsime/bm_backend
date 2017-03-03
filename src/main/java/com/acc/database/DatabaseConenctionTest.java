package com.acc.database;

import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.models.Group;
import com.acc.models.User;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    public static void main(String[] args) {
        UserRepository UR = new UserRepository();
        TagRepository TR = new TagRepository();
        GroupRepository GR = new GroupRepository();
        //TR.add(new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
        //ArrayList<Tag> tags = new ArrayList<>();
        //tags.add (new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
       // UR.add(new User("Alexis","Matrovic","shadyserbian@hotmail.com","alex.matrovic",tags));
        //System.out.println(Arrays.toString(UR.getQuery(new GetUserByIdSpec(1)).toArray()));
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1,"Alexis","Matrovic","shadyserbian@hotmail.com","alex.matrovic",null));
        Group group = new Group(1,"Shady Slavs",users);
        GR.add(group);
    }
}
