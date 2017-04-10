package com.acc.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
// TODO: 15.03.2017 implement group set 
@Entity
@Table(name = "TAG")
public class HbnTag implements Serializable, HbnEntity {

    private long id;
    private String tagName;
    private String description;
    private String type;
    private Set<HbnUser> users;
    private Set<HbnProblem> problems;
    private Set<HbnBachelorGroup> groups;


    public HbnTag() {
    }

    public HbnTag(String tagName, String description, String type) {
        this.tagName = tagName;
        this.description = description;
        this.type = type;
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

    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    public Set<HbnUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HbnUser> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    public Set<HbnProblem> getProblems() {
        return problems;
    }

    public void setProblems(Set<HbnProblem> problems) {
        this.problems = problems;
    }

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    public Set<HbnBachelorGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<HbnBachelorGroup> groups) {
        this.groups = groups;
    }
}

