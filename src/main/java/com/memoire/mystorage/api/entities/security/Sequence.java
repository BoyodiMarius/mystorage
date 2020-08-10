/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.entities.security;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PaulAbram
 */
@Entity
@Table(name = "sequences")
public class Sequence extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "count_sequence", nullable = false)
    private Integer countSequence = 0;

    public Sequence() {
    }

    public Sequence(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountSequence() {
        return countSequence;
    }

    public void setCountSequence(Integer countSequence) {
        this.countSequence = countSequence;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sequence other = (Sequence) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sequence{" + "id=" + id + ", countSequence=" + countSequence + '}';
    }

}
