package com.acc.database;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.Link;
import com.acc.models.Problem;
import com.acc.database.repository.UserRepository;
import com.acc.models.User;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;

import java.util.*;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    public static void main(String[] args) {

        UserRepository UR = new UserRepository();
        UR.add(new User("JayJay","Ronny","TøffeTog@oslo.no","johnny@accenture", null));


        List<Link> groups = new ArrayList<>();
        List<Link> tags = new ArrayList<>();
        Map<String, List<Link>> links = new HashMap<>();
        groups.add(new Link("group", "asd/asd/asd/1"));
        groups.add(new Link("group", "asd/asd/asd/2"));
        groups.add(new Link("group", "asd/asd/asd/3"));
        groups.add(new Link("group", "asd/asd/asd/4"));

        tags.add(new Link("tag", "asd/asd/asd/1"));
        tags.add(new Link("tag", "asd/asd/asd/2"));
        tags.add(new Link("tag", "asd/asd/asd/3"));
        tags.add(new Link("tag", "asd/asd/asd/4"));
        links.put("Groups", groups);
        links.put("Tags", tags);

        System.out.println(new Gson().toJson(links));

        /*System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserAllSpec()).toArray()));
        System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserByIdSpec(5)).toArray()));*/
        //UR.remove(7);

    }

}
