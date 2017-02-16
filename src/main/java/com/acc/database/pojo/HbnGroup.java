package com.acc.database.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
@Entity
@Table(name = "GROUP")
public class HbnGroup implements Serializable {

    private long id;
    private String name;
    private Set<HbnUser> users;
    private HbnProblem problem;

    public HbnGroup(){}

    public HbnGroup(long id, String name) {
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
    public Set<HbnUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HbnUser> hbnUsers) {
        this.users = hbnUsers;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    public HbnProblem getHbnProblem() {
        return problem;
    }

    public void setHbnProblem(HbnProblem problem) {
        this.problem = problem;
    }
}
