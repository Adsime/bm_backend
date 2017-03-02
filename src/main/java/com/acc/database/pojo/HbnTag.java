package com.acc.database.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */

@Entity
@Table(name = "TAG")
public class HbnTag implements Serializable, HbnEntity {

    private long id;
    private String tagName;
    private String description;
    private String type;
    private Set<HbnUser> users;
    private Set<HbnUser> problems;


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

    @ManyToMany(mappedBy = "tags")
    public Set<HbnUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HbnUser> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "tags")
    public Set<HbnUser> getProblems() {
        return problems;
    }

    public void setProblems(Set<HbnUser> problems) {
        this.problems = problems;
    }
}

