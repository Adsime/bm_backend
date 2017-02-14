package com.acc.database.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
@Entity
@Table(name = "GROUP")
public class Group {

    private long id;
    private String name;
    private Set<User> users;
    private Set<Institution> institutions;

    private Problem problem;

    public Group(){}

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "groups")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "groups")
    public Set<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
