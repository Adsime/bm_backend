package com.acc.database.pojo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 07.02.2017.
 */
@Entity
@Table(name = "PROBLEM")
public class Problem {

    private long id;
    private String path;
    private Set<Tag> tags;

    public Problem(){}

    public Problem(String path) {
        this.path = path;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "PROBLEM_TAG",
            joinColumns =  @JoinColumn(name = "tag_id"),
            inverseJoinColumns =  @JoinColumn(name = "problem_id")
    )
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
