package com.acc.database;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.specification.GetProblemAllSpec;
import com.acc.database.specification.GetProblemByIdSpec;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.models.Problem;
import com.acc.database.repository.UserRepository;
import com.acc.models.User;
import org.hibernate.SessionFactory;

import java.util.Arrays;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
public class DatabaseConenctionTest {

    public static void main(String[] args) {

        //UserRepository UR = new UserRepository();
        //UR.add(new User("Johnny","Ronny","TøffeTog@oslo.no","johnny@accenture"));

        /*System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserAllSpec()).toArray()));
        System.out.println("\n" + Arrays.toString(UR.getQuery(new GetUserByIdSpec(5)).toArray()));*/
        //UR.remove(7);

    }

}
