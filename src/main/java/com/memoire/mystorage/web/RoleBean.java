/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.Role;
import com.memoire.mystorage.services.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.services.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.RoleServiceBeanLocal;
import com.memoire.mystorage.shiro.EntityRealm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

import javax.annotation.PostConstruct;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "roleBean")
@ViewScoped
public class RoleBean implements Serializable {

    private Role role;
    private List<Role> roles;
    private List<Role> selectRoles;
    private List<Role> ajoutRoles;
    private List<Role> retraitRoles;
    private List<Profil> profils;
    private Profil selectProfil;
    private List<Role> mesRoles;
    private String profil;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private RoleServiceBeanLocal rsbl;
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;

    /**
     * Creates a new instance of RoleBean
     */
    public RoleBean() {
        this.role = new Role();
        ajoutRoles = new ArrayList<>();
        retraitRoles = new ArrayList<>();
        roles = new ArrayList<>();
        selectRoles = new ArrayList<>();
        selectProfil = new Profil();
        profils = new ArrayList<>();
        mesRoles = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        if (EntityRealm.getUser() != null) {
            profil = EntityRealm.getUser().getProfil().getNom();
            List<ProfilRole> l = this.prsbl.getBy("profil", EntityRealm.getUser().getProfil());
            for (ProfilRole p : l) {
                mesRoles.add(p.getRole());
            }
        }
    }

    public void setProfilRole() {
        selectRoles = this.prsbl.getProfilRoles(selectProfil);
    }

    public void modifierRole() {
        if (selectRoles != null) {
            //recherche des role des profil des personnels
            List<Role> profilRoles = prsbl.getProfilRoles(selectProfil);
            //si la liste des roles est vide il s'agit d'une insertion
            if (profilRoles.isEmpty() && !selectRoles.isEmpty()) {
                this.ajoutRolesPers(selectRoles);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Modification réussit !!!!!!!!", ""));

                return;
            }
            //si la liste des personne n'est pas vide et ls roles selectioné ne le sont pas on fait une suppression
            if (!profilRoles.isEmpty() && selectRoles.isEmpty()) {
                List<ProfilRole> profilRole = prsbl.getBy("profil", selectProfil);
                for (ProfilRole role2 : profilRole) {
                    prsbl.supProfilRoles(role2);
                }
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Modification réussit !!!!!!!!", ""));
                return;
            }
            //si la liste des role n'est pas vide et la liste des roles selectionné nest pas vide
            if (!profilRoles.isEmpty() && !selectRoles.isEmpty()) {
                //chercher les role que le personnel a retirer
                for (Role roleProfil : profilRoles) {
                    if (!selectRoles.contains(roleProfil)) {
                        retraitRoles.add(roleProfil);
                    }
                }
                //chercher les role a ajouter
                for (Role roleSelect : selectRoles) {
                    if (!profilRoles.contains(roleSelect)) {
                        ajoutRoles.add(roleSelect);
                    }
                }
                //ajout des roles
                if (!ajoutRoles.isEmpty()) {
                    ajoutRolesPers(ajoutRoles);
                }
                //retrait de role
                if (!retraitRoles.isEmpty()) {
                    for (Role catPersonneRole : retraitRoles) {
                        System.out.println(catPersonneRole);
                    }
                    for (Role role1 : retraitRoles) {
                        ProfilRole profilRol = prsbl.getProfilRoles(selectProfil, role1);
                        System.out.println(profilRol);
                        if (profilRol != null) {
                            prsbl.supProfilRoles(profilRol);
                        }
                    }
                }
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Modification réussit !!!!!!!!", ""));
            }
        }
    }

    public void ajoutRolesPers(List<Role> roles) {
        for (Role role1 : roles) {
            ProfilRole profilRole = new ProfilRole(selectProfil, role1);
            prsbl.saveOne(profilRole);
        }
    }

    public void saveRole() {
        this.rsbl.saveOne(role);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        roles = this.rsbl.getAll();
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getSelectRoles() {
        return selectRoles;
    }

    public void setSelectRoles(List<Role> selectRoles) {
        this.selectRoles = selectRoles;
    }

    public List<Role> getAjoutRoles() {
        return ajoutRoles;
    }

    public void setAjoutRoles(List<Role> ajoutRoles) {
        this.ajoutRoles = ajoutRoles;
    }

    public List<Role> getRetraitRoles() {
        return retraitRoles;
    }

    public void setRetraitRoles(List<Role> retraitRoles) {
        this.retraitRoles = retraitRoles;
    }

    public List<Profil> getProfils() {
        profils = this.psbl.getAll();
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public Profil getSelectProfil() {
        return selectProfil;
    }

    public void setSelectProfil(Profil selectProfil) {
        this.selectProfil = selectProfil;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public RoleServiceBeanLocal getRsl() {
        return rsbl;
    }

    public void setRsl(RoleServiceBeanLocal rsbl) {
        this.rsbl = rsbl;
    }

    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

    public List<Role> getMesRoles() {
        return mesRoles;
    }

    public void setMesRoles(List<Role> mesRoles) {
        this.mesRoles = mesRoles;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

}
