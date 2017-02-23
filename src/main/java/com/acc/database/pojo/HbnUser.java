package com.acc.database.pojo;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
public class HbnUser implements Serializable, HbnPOJO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;
    private String enterpriseId;
    private Set<HbnGroup> groups;
    private Set<HbnTag> tags;

    public HbnUser(){}

    public HbnUser(String firstName, String lastName, String email, String salt, String enterpriseId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.enterpriseId = enterpriseId;
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