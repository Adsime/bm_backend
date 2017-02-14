package com.acc.database.pojo;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private Set<Group> groups;
    private Set<Tag> tags;

    public User(){}

    public User(String firstName, String lastName, String email, String salt, Problem problem) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
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

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "GROUP_ASSOCIATE",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "group_id")
    )
    public Set<Group> getGroups(){
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_TAG",
            joinColumns =  @JoinColumn(name = "tag_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_id")
    )
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


}