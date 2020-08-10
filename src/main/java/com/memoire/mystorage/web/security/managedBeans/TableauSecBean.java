/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

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
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Administrateur
 */
@Named(value = "tableauSecBean")
@ViewScoped
public class TableauSecBean implements Serializable {

    private ProfilRole profilRole;
    private List<Role> rolees;
    private List<Utilisateur> utilisateurs;
    private List<Utilisateur> utilisateursFilter;
    private boolean visible = true;
    private MethodeJournalisation journalisation;

    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private RoleServiceBeanLocal rsbl;
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ModuleServiceBeanLocal msbl;
    @EJB
    private FonctionnaliteServiceBeanLocal fsbl;

    public TableauSecBean() {
        this.profilRole = new ProfilRole();
        this.rolees = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
        this.journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        try {
            this.utilisateurs = this.usbl.getAll();
//            this.rolees = this.rsbl.getAll();
//            for (Rolee rolee : rolees) {
//                if (rolee.getId() > 71L) {
//                    this.profilRole.setEtat(true);
//                    this.profilRole.setRole(rolee);
//                    this.profilRole.setProfil(this.psbl.getBy("nom", "Admin").get(0));
//                    System.out.println(this.psbl.getBy("nom", "Admin").get(0).getNom());
//                    this.prsbl.saveOne(profilRole);
//                    System.out.println(profilRole.getProfil().getNom() + " + " + profilRole.getRole().getNom());
//                    this.profilRole = new ProfilRole();
//                }
//            }

        } catch (Exception e) {
        }
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Utilisateur u = (Utilisateur) value;
        return u.getId().toString().contains(filterText)
                || u.getLogin().toLowerCase().contains(filterText);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public ProfilRole getProfilRole() {
        return profilRole;
    }

    public void setProfilRole(ProfilRole profilRole) {
        this.profilRole = profilRole;
    }

    public List<Role> getRolees() {
        return rolees;
    }

    public void setRolees(List<Role> rolees) {
        this.rolees = rolees;
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

    public List<Utilisateur> getUtilisateursFilter() {
        return utilisateursFilter;
    }

    public void setUtilisateursFilter(List<Utilisateur> utilisateursFilter) {
        this.utilisateursFilter = utilisateursFilter;
    }

}
