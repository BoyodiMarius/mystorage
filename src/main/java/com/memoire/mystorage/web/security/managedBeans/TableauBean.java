/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.FonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.project.resultview.web.utils.MethodeJournalisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Administrateur
 */
@Named(value = "tableauBean")
@ViewScoped
public class TableauBean implements Serializable {

    private List<Utilisateur> utilisateurs;
    private boolean visible = true;
    private MethodeJournalisation journalisation;
    private List<Role> roles;
    private List<Role> roles1;
    private Profil profil;
    private ProfilRole profilRole;

    @EJB
    RoleServiceBeanLocal rsbl;
    @EJB
    ProfilRoleServiceBeanLocal prsbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ModuleServiceBeanLocal msbl;
    @EJB
    private FonctionnaliteServiceBeanLocal fsbl;
    @EJB
    private ProfilServiceBeanLocal psbl;


    public TableauBean() {
        this.utilisateurs = new ArrayList<>();
        this.journalisation = new MethodeJournalisation();
        this.roles = new ArrayList<>();
        this.roles1 = new ArrayList<>();
        this.profil = new Profil();
        this.profilRole = new ProfilRole();
    }

    @PostConstruct
    public void init() {
        try {
//
//            this.utilisateursJuridictions = this.ujsbl.getAll();
            this.roles = this.rsbl.getAll();
            for (Role role : roles) {
                if (role.getId() > 71) {
                    roles.add(role);
                }
            }

            for (Role rolee : roles1) {
                this.profilRole.setRole(rolee);
                this.profilRole.setProfil(this.psbl.getOneBy("nom", "Admin"));
                this.prsbl.saveOne(profilRole);
                this.profilRole = new ProfilRole();
            }

        } catch (Exception e) {
        }
    }

    public Long modules() {
        Long mod = 0L;
        try {
            mod = this.msbl.count();
        } catch (Exception e) {
        }
        return mod;
    }

    public Long fonctionnalite() {
        Long fon = 0L;
        try {
            fon = this.fsbl.count();
        } catch (Exception e) {
        }
        return fon;
    }

    public Long profil() {
        Long pro = 0L;
        try {
            pro = this.psbl.count();
        } catch (Exception e) {
        }
        return pro;
    }

    public Long users() {
        Long use = 0L;
        try {
            use = this.usbl.count();
        } catch (Exception e) {
        }
        return use;
    }

    public List<Utilisateur> getUtilisateurs() {
        try {
            this.utilisateurs = this.usbl.getAll();
        } catch (Exception e) {
        }
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
