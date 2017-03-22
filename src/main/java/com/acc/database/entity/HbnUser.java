package com.acc.database.entity;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
public class HbnUser implements Serializable, HbnEntity {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private String enterpriseId;
    private String accessLevel;
    private Set<HbnBachelorGroup> groups;
    private Set<HbnTag> tags;
    private Set<HbnProblem> problems;

    public HbnUser(){}

    public HbnUser(String firstName, String lastName, String email, String salt, String enterpriseId, String accessLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.enterpriseId = enterpriseId;
        this.accessLevel = accessLevel;
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

    @Column(name = "enterprise_id")
    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Column(name = "access_level")
    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    public Set<HbnBachelorGroup> getGroups(){
        return groups;
    }

    public void setGroups(Set<HbnBachelorGroup> groups) {
        this.groups = groups;
    }

    @ManyToMany(fetch = FetchType.EAGER)
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

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    public Set<HbnProblem> getProblems() {
        return problems;
    }

    public void setProblems(Set<HbnProblem> problems) {
        this.problems = problems;
    }
}