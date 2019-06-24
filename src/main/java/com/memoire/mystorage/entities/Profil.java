/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "Profil")
public class Profil extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "nom", nullable = true)
    private String nom;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    private Set<ProfilRole> profilRoles = new HashSet<ProfilRole>();

    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    private Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();

    public Profil() {
        
    }

    public Profil(String nom) {
        
        this.nom = nom;

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ProfilRole> getProfilRoles() {
        return profilRoles;
    }

    public void setProfilRoles(Set<ProfilRole> profilRoles) {
        this.profilRoles = profilRoles;
    }

    public Set<Utilisateur> getPersonnes() {
        return utilisateurs;
    }

    public void setPersonnes(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
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
        hash = 79 * hash + Objects.hashCode(this.Id);
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
        final Profil other = (Profil) obj;
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profil{" + "Id=" + Id + ", nom=" + nom + ", libelle=" + libelle + ", profilRoles=" + profilRoles + ", utilisateurs=" + utilisateurs + '}';
    }

}
