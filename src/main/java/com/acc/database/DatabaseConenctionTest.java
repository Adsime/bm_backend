package com.acc.database;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.Link;
import com.acc.models.Problem;
import com.acc.database.repository.TagRepository;


import com.acc.database.repository.UserRepository;
import com.acc.models.Tag;
import com.acc.models.User;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    public static void main(String[] args) {

        UserRepository UR = new UserRepository();
        TagRepository TR = new TagRepository();
        //TR.add(new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "FETPERSON","KULHETS-TAG","FOR KULE OG FETE PERSONER"));
        UR.add(new User("Per","Øtreveit","feteper@hotmail.com","øtreveit.per",tags));



        //Arrays.toString(UR.getQuery(new GetUserByIdSpec(9)).toArray());
        //UR.add(new User(1,"David","Stjernefi","TøffeTog@oslo.no","johnny@accenture",groupIds,tags));
        /*System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserAllSpec()).toArray()));
        System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserByIdSpec(5)).toArray()));*/
        //UR.remove(7);
    }

}
