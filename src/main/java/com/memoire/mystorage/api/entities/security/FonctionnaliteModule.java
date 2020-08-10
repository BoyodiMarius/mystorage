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
 * @author Administrateur
 */
@Entity
@Table(name = "fonctionnalite_modules")
public class FonctionnaliteModule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "etat")
    private Boolean etat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_affectation")
    private Date dateAffectation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_revocation")
    private Date dateRevocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonctionnalite", updatable = true, insertable = true)
    private Fonctionnalite fonctionnalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modules", insertable = true, updatable = true)
    private Module modules;

    public FonctionnaliteModule() {
    }

    public FonctionnaliteModule(Module modules, Fonctionnalite fonctionnalite) {
        this.modules = modules;
        this.fonctionnalite = fonctionnalite;
    }

    public FonctionnaliteModule(Module modules, Fonctionnalite fonctionnalite, Boolean etat) {
        this.etat = etat;
        this.fonctionnalite = fonctionnalite;
        this.modules = modules;
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

    public Fonctionnalite getFonctionnalite() {
        return fonctionnalite;
    }

    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

    public Module getModules() {
        return modules;
    }

    public void setModules(Module modules) {
        this.modules = modules;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Utilisateur getUtilisateurAction() {
        return utilisateurAction;
    }

    public void setUtilisateurAction(Utilisateur utilisateurAction) {
        this.utilisateurAction = utilisateurAction;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final FonctionnaliteModule other = (FonctionnaliteModule) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FonctionnaliteModule{" + "id=" + id + ", etat=" + etat + ", dateAffectation=" + dateAffectation + ", dateRevocation=" + dateRevocation + ", dateCreation=" + dateCreation + ", dateDerniereModification=" + dateDerniereModification + ", fonctionnalite=" + fonctionnalite + ", modules=" + modules + '}';
    }

}
