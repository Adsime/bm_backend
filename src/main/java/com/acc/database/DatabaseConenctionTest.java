package com.acc.database;

import com.acc.database.repository.GroupRepository;
import com.acc.database.repository.ProblemRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetGroupAllSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.GetUserByTagSpec;
import com.acc.models.Group;
import com.acc.models.Problem;
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
        GroupRepository GR = new GroupRepository();
        ProblemRepository PR = new ProblemRepository();

        for(User u : UR.getQuery(new GetUserByTagSpec(
                Arrays.asList(1, 2, 3), false))) {
            System.out.println(u);
        }

        Tag tag = new Tag(3,"2016","FISCAL YEAR","READ THE TYPE DIP SHIT");
        //TR.add(tag);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Problem problem = new Problem(1,3,"Zlatan App","","Z://ZlatanOS/Zlatan",tags);

        PR.add(problem);
        //UR.update(new User("Alexis","Matrovic","shadyserbian@hotmail.com","alex.matrovic",tags));
        //GR.update(new Group(1,"Spurs Gang", null));
        //ArrayList<User> users = new ArrayList<>();
        //UR.add(new User(5,"Zlatan","Ibrahimovic","zlatan@zlatan-mail.com","zlatan.zlatan",null));
        //Group group = new Group(6,"Zlatan's Financial Group", null, null,null);
        //GR.add(group);
        //System.out.println(Arrays.toString(GR.getQuery(new GetGroupAllSpec()).toArray()));
        //GR.remove(4);
        //GR.update(group);
    }
}
