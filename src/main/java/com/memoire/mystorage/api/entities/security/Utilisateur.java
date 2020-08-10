/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.entities.security;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Brendev
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "login")
    private String login;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "profilactif")
    private Boolean profilactif;

    @Column(name = "question")
    private String question;

    @Column(name = "reponse")
    private String reponse;

    @Column(name = "photo")
    private String photo;

    @JoinColumn(name = "personnes", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonnePattern personnePattern;

    public Utilisateur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Boolean getProfilactif() {
        return profilactif;
    }

    public void setProfilactif(Boolean profilactif) {
        this.profilactif = profilactif;
    }

    public PersonnePattern getPersonnePattern() {
        return personnePattern;
    }

    public void setPersonnePattern(PersonnePattern personnePattern) {
        this.personnePattern = personnePattern;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", login=" + login + ", passwd=" + passwd + ", profilactif=" + profilactif + ", question=" + question + ", reponse=" + reponse + ", photo=" + photo + ", personnePattern=" + personnePattern + '}';
    }

}
