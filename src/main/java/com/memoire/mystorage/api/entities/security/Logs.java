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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Brendev
 */
@Entity
@Table(name = "logs")
public class Logs extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "log_action")
    private String logAction;

    @Column(name = "log_date")
    @Temporal(TemporalType.DATE)
    private Date logDate;

    @Column(name = "log_date_heure")
    private String logDateHeure;

    @Column(name = "log_remote_ip")
    private String logRemoteIp;

    @Column(name = "log_remote_mac")
    private String logRemoteMac;

    public Logs() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getLogAction() {
        return logAction;
    }

    public void setLogAction(String logAction) {
        this.logAction = logAction;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogDateHeure() {
        return logDateHeure;
    }

    public void setLogDateHeure(String logDateHeure) {
        this.logDateHeure = logDateHeure;
    }

    public String getLogRemoteIp() {
        return logRemoteIp;
    }

    public void setLogRemoteIp(String logRemoteIp) {
        this.logRemoteIp = logRemoteIp;
    }

    public String getLogRemoteMac() {
        return logRemoteMac;
    }

    public void setLogRemoteMac(String logRemoteMac) {
        this.logRemoteMac = logRemoteMac;
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

    public Date getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(Date dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.Id);
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
        final Logs other = (Logs) obj;
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logs{" + "Id=" + Id + ", logAction=" + logAction + ", logDate=" + logDate + ", logDateHeure=" + logDateHeure + ", logRemoteIp=" + logRemoteIp + ", logRemoteMac=" + logRemoteMac + '}';
    }

}
