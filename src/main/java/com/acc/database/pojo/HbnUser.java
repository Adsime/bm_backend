package com.acc.database.pojo;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
public class HbnUser implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private Set<HbnGroup> groups;
    private Set<HbnInstitution> institutions;
    private Set<HbnTag> tags;

    public HbnUser(){}

    public HbnUser(String firstName, String lastName, String email, String salt) {
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
    public Set<HbnGroup> getGroups(){
        return groups;
    }

    public void setGroups(Set<HbnGroup> groups) {
        this.groups = groups;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "INSTITUTION_AFFILIATION",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "institution_id")
    )
    public Set<HbnInstitution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Set<HbnInstitution> hbnInstitutions) {
        this.institutions = hbnInstitutions;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_TAG",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "tag_id")
    )
    public Set<HbnTag> getTags() {
        return tags;
    }

    public void setTags(Set<HbnTag> hbnTags) {
        this.tags = hbnTags;
    }


}