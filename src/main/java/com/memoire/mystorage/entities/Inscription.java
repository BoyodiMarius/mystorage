/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

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
 * @author user
 */
@Entity
@Table(name = "Inscription")
public class Inscription extends BaseEntities {

    @Id //utilisateur
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateinscription")
    private Date dateinscription;

    @Temporal(TemporalType.DATE)
    @Column(name = "datedebinscription")
    private Date datebebinscription;

    @Temporal(TemporalType.DATE)
    @Column(name = "datefninscription")
    private Date datefninscription;

    @Column(name = "nbretranches")
    private Integer nbretranches;

    @Column(name = "etat")
    private boolean etat = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "particulier")
    private Particulier particulier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeModule")
    private Typemodule typemodule;
    
     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion")
    private Promotion promotion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annee")
    private Annee annee;

    public Inscription() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateinscription() {
        return dateinscription;
    }

    public void setDateinscription(Date dateinscription) {
        this.dateinscription = dateinscription;
    }

    public Date getDatebebinscription() {
        return datebebinscription;
    }

    public void setDatebebinscription(Date datebebinscription) {
        this.datebebinscription = datebebinscription;
    }

    public Date getDatefninscription() {
        return datefninscription;
    }

    public void setDatefninscription(Date datefninscription) {
        this.datefninscription = datefninscription;
    }

    public Integer getNbretranches() {
        return nbretranches;
    }

    public void setNbretranches(Integer nbretranches) {
        this.nbretranches = nbretranches;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Particulier getParticulier() {
        return particulier;
    }

    public void setParticulier(Particulier particulier) {
        this.particulier = particulier;
    }

    public Typemodule getTypemodule() {
        return typemodule;
    }

    public void setTypemodule(Typemodule typemodule) {
        this.typemodule = typemodule;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Inscription other = (Inscription) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inscription{" + "id=" + id + ", dateinscription=" + dateinscription + ", datebebinscription=" + datebebinscription + ", datefninscription=" + datefninscription + ", nbretranches=" + nbretranches + ", etat=" + etat + ", particulier=" + particulier + ", typemodule=" + typemodule + ", promotion=" + promotion + ", annee=" + annee + '}';
    }

   
}
