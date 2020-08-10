/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import com.memoire.mystorage.api.entities.security.Utilisateur;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author user
 */
@Entity
@DiscriminatorValue(value = "P")
public class Personnel extends Utilisateur {

    @Column(name = "poste", nullable = true)
    private String poste;

    public Personnel(String poste) {
        this.poste = poste;
    }

    public Personnel() {
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Personnel{" + "poste=" + poste + '}';
    }

}
