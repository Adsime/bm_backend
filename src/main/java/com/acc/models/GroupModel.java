package com.acc.models;

import java.util.List;

/**
 * Created by melsom.adrian on 26.01.2017.
 */
public class GroupModel {
    String name, school;
    int id;
    List<Student> studentList;
    List<Supervisor> supervisorList;

    public Group(String name, String school, int id, List<Student> studentList, List<Supervisor> supervisorList) {
        this.name = name;
        this.school = school;
        this.id = id;
        this.studentList = studentList;
        this.supervisorList = supervisorList;
    }

    @Override
public String toString() {
        return "GroupModel{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", id=" + id +
                ", studentList=" + studentList +
                ", supervisorList=" + supervisorList +
                '}';
    }
}
