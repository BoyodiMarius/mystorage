/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.entities.security;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Brendev
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Version
    @Column(name = "version", nullable = false)
    protected Integer version = 1;

    @JoinColumn(name = "utilisateur", updatable = true, insertable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    protected Utilisateur utilisateurAction;

    @Column(name = "date_derniere_modification")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateDerniereModification;

    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreation = new Date();

}
