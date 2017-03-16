package com.acc.database;

import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.ProblemRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserByTagSpec;
import com.acc.models.Problem;
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
        GroupRepository GR = new GroupRepository();
        ProblemRepository PR = new ProblemRepository();

        for(User u : UR.getQuery(new GetUserByTagSpec(
                Arrays.asList("student"), false))) {
            System.out.println(u);
        }

        Tag tag = new Tag(3,"2016","FISCAL YEAR","READ THE TYPE DIP SHIT");
        //TR.add(tag);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Problem problem = new Problem(1,3,"Zlatan App","","Z://ZlatanOS/Zlatan",tags);

        PR.add(problem);

        Tag t1 = new Tag(1,"CHOKERS","TEAM TYPE","THEY CHOKE, YO");
        Tag t2 = new Tag(4,"ZLATAN","ZLATAN","ONLY ZLATAN");
        Tag t3 = new Tag(3,"BOOK","MEDIA","SELF EXPLANATORY, YOU DAFT CUNT");

        List<Tag> booktag = new ArrayList<>();
        booktag.add(t3);

        List<Tag> lt2 = new ArrayList<>();
        lt2.add(t2);

        Problem p1 = new Problem(1,3,"Dont take things for granted","WE BLEW THE 4-0","NOT TO CL FINAL",booktag);
        Problem p2 = new Problem(6,5,"To Zlatan, Love Zlatann","Zlatan","Zlatan", new ArrayList<>());
        Problem p3 = new Problem(6,2,"To Zlatan, Love Zlatann","Zlatan","Zlatan", new ArrayList<>());

        User u1 = new User("David", "Silva", "merlin@mcfc.co.uk", "silva.d","1", null);
        User u2 = new User(4,"Zlatan", "Ibrahimovic", "zlatan@zlatan.se", "zlatan.zlatan", "10",lt2);
        User u3 = new User("PSG", "FC", "blew40@cl", "cash.fail","0", null);
    }
}
