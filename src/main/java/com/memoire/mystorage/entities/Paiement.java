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
@Table(name = "Paiement")
public class Paiement extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libellePaiement", nullable = true)
    private String libellepaiement;

    @Column(name = "montantpaiement", nullable = true)
    private String montantpaiement;

    @Column(name = "modepaiement", nullable = true)
    private String modepaiement;

    @Column(name = "typefrais", nullable = true)
    private String typefrais;

    @Temporal(TemporalType.DATE)
    @Column(name = "datePaiement", nullable = true)
    private Date datepaiement;

    @Column(name = "etat")
    private boolean etat = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inscription", insertable = true, updatable = true)
    private Inscription inscription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion")
    private Promotion promotion;

    public Paiement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibellepaiement() {
        return libellepaiement;
    }

    public void setLibellepaiement(String libellepaiement) {
        this.libellepaiement = libellepaiement;
    }

    public String getMontantpaiement() {
        return montantpaiement;
    }

    public void setMontantpaiement(String montantpaiement) {
        this.montantpaiement = montantpaiement;
    }

    public String getModepaiement() {
        return modepaiement;
    }

    public void setModepaiement(String modepaiement) {
        this.modepaiement = modepaiement;
    }

    public String getTypefrais() {
        return typefrais;
    }

    public void setTypefrais(String typefrais) {
        this.typefrais = typefrais;
    }

    public Date getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(Date datepaiement) {
        this.datepaiement = datepaiement;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
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
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Paiement other = (Paiement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paiement{" + "id=" + id + ", libellepaiement=" + libellepaiement + ", montantpaiement=" + montantpaiement + ", modepaiement=" + modepaiement + ", typefrais=" + typefrais + ", datepaiement=" + datepaiement + ", etat=" + etat + ", inscription=" + inscription + ", promotion=" + promotion + '}';
    }

}
