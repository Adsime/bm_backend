package com.acc.database.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
@Entity
@Table(name = "INSTITUTION")
public class HbnInstitution implements Serializable {

    private long id;
    private String name;
    private Set<HbnUser> users;

    public HbnInstitution(){}

    public HbnInstitution(String name){
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

    @ManyToMany(mappedBy = "institutions")
    public Set<HbnUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HbnUser> hbnUsers) {
        this.users = hbnUsers;
    }
}
