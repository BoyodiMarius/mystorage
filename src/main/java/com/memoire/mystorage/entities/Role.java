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
 * @author user
 */
@Entity
@Table(name = "Role")
public class Role extends BaseEntities {

    @Id //utilisateur
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Libelle", nullable = true)
    private String Libelle;

      @Column(name = "nom",nullable = true)
    private String nom;
      
      

    public Role() {
    }

    public Role(String nom) {
        this.nom = nom;
    }
    

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Role(Integer id, String Libelle, String nom, String Description) {
        this.id = id;
        this.Libelle = Libelle;
        this.nom = nom;

    }

    public Role(Integer id, String Libelle, String Description) {
        this.id = id;
        this.Libelle = Libelle;
   
    }

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return Libelle;
    }

   

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLibelle(String Libelle) {
        this.Libelle = Libelle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.Libelle);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (!Objects.equals(this.Libelle, other.Libelle)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", Libelle=" + Libelle + ", nom=" + nom + '}';
    }

 

}
