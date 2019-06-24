/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Armel
 */
@Entity
@Table(name = "Typemodule")
public class Typemodule extends BaseEntities {

    @Id //utilisateur
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prixmodule")
    private Double prixmodule;

    @Column(name = "duree")
    private String duree;

    @Column(name = "etat")
    private boolean etat = false;

    @Column(name = "photo")
    private String photo;

    public Typemodule() {
    }

    public Typemodule(Integer id, String libelle, Double prixmodule, String duree, String photo) {
        this.id = id;
        this.libelle = libelle;
        this.prixmodule = prixmodule;
        this.duree = duree;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixmodule() {
        return prixmodule;
    }

    public void setPrixmodule(Double prixmodule) {
        this.prixmodule = prixmodule;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Typemodule other = (Typemodule) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return "Typemodule{" + "id=" + id + ", libelle=" + libelle + ", prixmodule=" + prixmodule + ", duree=" + duree + ", etat=" + etat + ", photo=" + photo + '}';
    }

}
