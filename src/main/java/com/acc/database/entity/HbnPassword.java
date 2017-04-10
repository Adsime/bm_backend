package com.acc.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nguyen.duy.j.khac on 08.02.2017.
 */
@Entity
@Table(name = "PASSWORD")
public class HbnPassword implements Serializable, HbnEntity {

    private long id;
    private String passHash;
    private String eIdHash;

    public HbnPassword(String passHash, String eIdHash) {
        this.passHash = passHash;
        this.eIdHash = eIdHash;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }


    @Column(name = "pass_hash")
    public String getPassHash() {
        return passHash;
    }

    @Column(name = "eid_hash")
    public String getEIdHash() {
        return eIdHash;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }


    public void setEIdHash(String eIdHash) {
        this.eIdHash = eIdHash;
    }
}
