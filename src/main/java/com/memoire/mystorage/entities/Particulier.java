/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author user
 */
@Entity
@DiscriminatorValue(value = "C")
public class Particulier extends Utilisateur {

    @Column(name = "typesecure")
    private String typesecure;

    @Column(name = "option", nullable = true)
    private String option;

    public Particulier() {
    }

    public String getTypesecure() {
        return typesecure;
    }

    public void setTypesecure(String typesecure) {
        this.typesecure = typesecure;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Particulier{" + "typesecure=" + typesecure + ", option=" + option + '}';
    }

}
