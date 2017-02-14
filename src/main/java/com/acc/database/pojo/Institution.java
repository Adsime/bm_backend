package com.acc.database.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
@Entity
@Table(name = "INSTITUTION")
public class Institution {

    private long id;
    private String name;
    private Set<Group> groups;

    public Institution(){}

    public Institution(String name){
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "INSTITUTION_AFFILIATION",
            joinColumns =  @JoinColumn(name = "group_id"),
            inverseJoinColumns =  @JoinColumn(name = "institution_id")
    )
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
