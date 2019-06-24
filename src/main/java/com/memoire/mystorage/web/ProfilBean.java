/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.services.ProfilServiceBeanLocal;
import com.memoire.mystorage.utils.constantes.Constante;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Armel
 */
@Named(value = "profilBean")
@ViewScoped
public class ProfilBean implements Serializable {

    private Profil profil;
    private List<Profil> profils;
    @EJB
    private ProfilServiceBeanLocal psbl;

    /**
     * Creates a new instance of CategoriePersonnelBean
     */
    public ProfilBean() {
        this.profils = new ArrayList<>();
        this.profil = new Profil();
    }

    public void cancel(ActionEvent actionEvent) {
        this.profil = new Profil();
    }

    public void save(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.profil.getId() == null) {
                this.psbl.saveOne(profil);
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
            } else {
                this.psbl.updateOne(profil);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void getObject(Integer id) {
        this.profil = this.psbl.find(id);
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Profil> getProfils() {
        profils = this.psbl.getAll();
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

   
}