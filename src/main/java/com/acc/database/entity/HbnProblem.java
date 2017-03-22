package com.acc.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
@Entity
@Table(name = "PROBLEM")
public class HbnProblem implements Serializable, HbnEntity {

    private long id;
    private String path;
    private Set<HbnTag> tags;
    private HbnUser user;
    private String title;

    public HbnProblem(){}

    public HbnProblem(String path, HbnUser hbnUser, String title) {
        this.path = path;
        this.user = hbnUser;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PROBLEM_TAG",
            joinColumns =  @JoinColumn(name = "problem_id"),
            inverseJoinColumns =  @JoinColumn(name = "tag_id")
    )
    public Set<HbnTag> getTags() {
        return tags;
    }

    public void setTags(Set<HbnTag> hbnTags) {
        this.tags = hbnTags;
    }

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public HbnUser getUser() {
        return user;
    }

    public void setUser(HbnUser hbnUser) {
        this.user = hbnUser;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
