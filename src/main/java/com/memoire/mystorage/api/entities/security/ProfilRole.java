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
@Table(name = "profil_roles")
public class ProfilRole extends BaseEntity {

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
    @JoinColumn(name = "profil", insertable = true, updatable = true)
    private Profil profil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolee", insertable = true, updatable = true)
    private Role role;

    public ProfilRole() {
    }

    public ProfilRole(Profil profil, Role role) {
        this.profil = profil;
        this.role = role;
    }

    public ProfilRole(Profil profil, Role role, Boolean etat) {
        this.profil = profil;
        this.role = role;
        this.etat = etat;
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

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final ProfilRole other = (ProfilRole) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfilRole{" + "id=" + id + ", etat=" + etat + ", dateAffectation=" + dateAffectation + ", dateRevocation=" + dateRevocation + ", dateCreation=" + dateCreation + ", dateDerniereModification=" + dateDerniereModification + ", profil=" + profil + ", role=" + role + '}';
    }

}
