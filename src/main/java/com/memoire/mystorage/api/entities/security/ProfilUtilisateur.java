/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.entities.security;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Brendev
 */
@Entity
@Table(name = "profil_utilisateurs")
public class ProfilUtilisateur extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "etat")
    private Boolean etat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_affectation")
    private Date dateAffectation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_revocation")
    private Date dateRevocation;

    @JoinColumn(name = "profil", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profil profil;

    @JoinColumn(name = "utilisateur_profil", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur utilisateurProfil;

    public ProfilUtilisateur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Utilisateur getUtilisateurProfil() {
        return utilisateurProfil;
    }

    public void setUtilisateurProfil(Utilisateur utilisateurProfil) {
        this.utilisateurProfil = utilisateurProfil;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateRevocation() {
        return dateRevocation;
    }

    public void setDateRevocation(Date dateRevocation) {
        this.dateRevocation = dateRevocation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(Date dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Utilisateur getUtilisateurAction() {
        return utilisateurAction;
    }

    public void setUtilisateurAction(Utilisateur utilisateurAction) {
        this.utilisateurAction = utilisateurAction;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ProfilUtilisateur other = (ProfilUtilisateur) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfilUtilisateur{" + "id=" + id + ", etat=" + etat + ", dateAffectation=" + dateAffectation + ", dateRevocation=" + dateRevocation + ", profil=" + profil + ", utilisateurProfil=" + utilisateurProfil + '}';
    }

}
