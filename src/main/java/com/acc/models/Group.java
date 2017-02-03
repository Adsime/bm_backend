package com.acc.models;

import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class Group {
    String name, school;
    int assignment;
    List<Student> studentList;
    List<Supervisor> supervisorList;

    @Override
public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", assignment=" + assignment +
                ", studentList=" + studentList +
                ", supervisorList=" + supervisorList +
                '}';
    }
}
