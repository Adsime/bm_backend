package com.acc.database;

import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.models.Tag;
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
        //TR.add(new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
        //ArrayList<Tag> tags = new ArrayList<>();
        //tags.add (new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
       // UR.add(new User("Alexis","Matrovic","shadyserbian@hotmail.com","alex.matrovic",tags));
        //System.out.println(Arrays.toString(UR.getQuery(new GetUserByIdSpec(1)).toArray()));
        System.out.println(Arrays.toString(UR.getQuery(new GetUserAllSpec()).toArray()));
    }
}
