package com.acc.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
// TODO: 15.03.2017 implement tag set 
@Entity
@Table(name = "BACHELOR_GROUP")
public class HbnBachelorGroup implements Serializable, HbnEntity {
    private long id;
    private String name;
    private Set<HbnUser> users;
    private HbnDocument document;
    private Set<HbnDocument> groupDocuments;
    private Set<HbnTag> tags;

    public HbnBachelorGroup(){}
    public HbnBachelorGroup(String name) {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_ASSOCIATE",
            joinColumns =  @JoinColumn(name = "bachelor_group_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_id")
    )
    public Set<HbnUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HbnUser> hbnUsers) {
        this.users = hbnUsers;
    }

    @OneToOne
    @JoinColumn(name = "document_id")
    public HbnDocument getDocument() {
        return document;
    }

    public void setDocument(HbnDocument document) {
        this.document = document;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_DOCUMENT",
            joinColumns =  @JoinColumn(name = "bachelor_group_id"),
            inverseJoinColumns =  @JoinColumn(name = "document_id")
    )
    public Set<HbnDocument> getGroupDocuments() {
        return groupDocuments;
    }

    public void setGroupDocuments(Set<HbnDocument> groupDocuments) {
        this.groupDocuments = groupDocuments;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_TAG",
            joinColumns =  @JoinColumn(name = "bachelor_group_id"),
            inverseJoinColumns =  @JoinColumn(name = "tag_id")
    )
    public Set<HbnTag> getTags() {
        return tags;
    }

    public void setTags(Set<HbnTag> tags) {
        this.tags = tags;
    }
}
