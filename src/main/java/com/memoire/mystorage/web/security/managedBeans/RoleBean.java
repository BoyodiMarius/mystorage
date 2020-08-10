/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilUtilisateurServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleFonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.project.resultview.web.utils.Constante;
import com.project.resultview.web.utils.MethodeJournalisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.omnifaces.util.Ajax;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "roleBean")
@ViewScoped
public class RoleBean implements Serializable {

    private Module modules;
    private List<Module> moduleses;
    private Role role;
    private List<Role> roles;
    private List<Role> selectRoles;
    private List<Role> ajoutRoles;
    private List<Role> retraitRoles;
    private List<Profil> profils;
    private Profil selectProfil;
    private String profil;
    private boolean visible;
    private List<ProfilUtilisateur> profilUtilisateurs;
    private boolean selectAll;
    private List<Utilisateur> utilisateurs;
    private List<RoleFonctionnalite> roleFonctionnalites;
    private List<RoleFonctionnalite> roleFonctionnalites1;
    private MethodeJournalisation journalisation;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private RoleServiceBeanLocal rsl;
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;
    @EJB
    protected ProfilUtilisateurServiceBeanLocal pudbl;
    @EJB
    private ModuleServiceBeanLocal msbl;
    @EJB
    private RoleFonctionnaliteServiceBeanLocal rfsbl;

    /**
     * Creates a new instance of RoleBean
     */
    public RoleBean() {
        this.modules = new Module();
        this.moduleses = new ArrayList<>();
        role = new Role();
        ajoutRoles = new ArrayList<>();
        retraitRoles = new ArrayList<>();
        roles = new ArrayList<>();
        selectRoles = new ArrayList<>();
        selectProfil = new Profil();
        profils = new ArrayList<>();
        this.roleFonctionnalites = new ArrayList<>();
        this.profilUtilisateurs = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            this.roleFonctionnalites = this.prsbl.getFonctionnalitesModules(this.modules);
            this.moduleses = this.msbl.getAll("libelle", true);
            if (LoginBean.currentSubject().hasRole("Consulter Profil-Role") || LoginBean.currentSubject().hasRole("Associer Profil-Role")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(RoleBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  roles.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }

        } catch (Exception e) {
        }

    }

    public void initmodule() {
        this.roleFonctionnalites = this.prsbl.getFonctionnalitesModules(this.modules);
    }

    public void onSelectAll() {
        selectRoles = selectAll ? roles : null;
    }

    public void setProfilRole() {
        roleFonctionnalites1 = this.prsbl.getFonctionnalites(selectProfil);
    }

    public void chooseRoles() {
        selectRoles.clear();
        for (RoleFonctionnalite rf : roleFonctionnalites1) {
            if(!selectRoles.contains(rf.getRole())){
                this.selectRoles.add(rf.getRole());
            }

        }
        System.out.println("selectRoles :"+selectRoles);
        System.out.println("selectRoles :"+selectRoles.size());
    }

    public void modifierRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            if (selectRoles != null) {
                //recherche des role des profil des personnels
                List<Role> profilRoles = prsbl.getProfilRoles(selectProfil);
                //si la liste des roles est vide il s'agit d'une insertion
                if (profilRoles.isEmpty() && !selectRoles.isEmpty()) {
                    this.ajoutRolesPers(selectRoles);
                    System.out.println("Test1");
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Modification réussit !!!!!!!!", ""));

                }
                //si la liste des roles n'est pas vide et ls roles selectioné ne le sont pas on fait une suppression
                if (!profilRoles.isEmpty() && selectRoles.isEmpty()) {
                    List<ProfilRole> profilRole = prsbl.getBy("profil", selectProfil);
                    for (ProfilRole role2 : profilRole) {
                        prsbl.supProfilRoles(role2);
                    }
                    System.out.println("Test2");
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Modification réussit !!!!!!!!", ""));
                }
                //si la liste des role n'est pas vide et la liste des roles selectionné nest pas vide
                if (!profilRoles.isEmpty() && !selectRoles.isEmpty()) {
                    //chercher les role que le personnel a retirer
                    for (Role roleProfil : profilRoles) {
                        if (!selectRoles.contains(roleProfil)) {
                            retraitRoles.add(roleProfil);
                        }
                    }
//                    //chercher les role a ajouter
                    for (Role roleSelect : selectRoles) {
                        if (!profilRoles.contains(roleSelect)) {
                            ajoutRoles.add(roleSelect);
                        }
                    }
//                    //ajout des roles
                    if (!ajoutRoles.isEmpty()) {
                        ajoutRolesPers(ajoutRoles);
                    }
//                    //retrait de role
                    if (!retraitRoles.isEmpty()) {
                        System.out.println("retraitRoles :" + retraitRoles);
                        for (Role role1 : retraitRoles) {
                            ProfilRole profilRol = prsbl.getProfilRoles(selectProfil, role1);
                            if (profilRol != null) {
                                System.out.println("Sup :" + prsbl.supProfilRoles(profilRol));
                            }
                        }
                    }
                    System.out.println("Test3");
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constante.ENREGISTREMENT_REUSSIE, ""));
                }
            }
            this.selectProfil = new Profil();
            this.modules = new Module();
            this.initmodule();
            this.setProfilRole();

            ajoutRoles.clear();
            retraitRoles.clear();
            selectRoles.clear();
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(RoleBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }

    }

    public void ajoutRolesPers(List<Role> roles) {
        try {
            for (Role role1 : roles) {
                ProfilRole profilRole = new ProfilRole(selectProfil, role1, true);
                prsbl.saveOne(profilRole);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void saveRole() {
        try {
            this.rsl.saveOne(role);
        } catch (Exception e) {
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        try {
            roles = this.rsl.getAll();
        } catch (Exception e) {
        }
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
        try {
            profils = this.psbl.getAll();
        } catch (Exception e) {
        }
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public RoleServiceBeanLocal getRsl() {
        return rsl;
    }

    public void setRsl(RoleServiceBeanLocal rsl) {
        this.rsl = rsl;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Profil getSelectProfil() {
        return selectProfil;
    }

    public void setSelectProfil(Profil selectProfil) {
        this.selectProfil = selectProfil;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<ProfilUtilisateur> getProfilUtilisateurs() {
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(List<ProfilUtilisateur> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }

    public Module getModules() {
        return modules;
    }

    public void setModules(Module modules) {
        this.modules = modules;
    }

    public List<Module> getModuleses() {
        return moduleses;
    }

    public void setModuleses(List<Module> moduleses) {
        this.moduleses = moduleses;
    }

    public List<RoleFonctionnalite> getRoleFonctionnalites() {
        return roleFonctionnalites;
    }

    public void setRoleFonctionnalites(List<RoleFonctionnalite> roleFonctionnalites) {
        this.roleFonctionnalites = roleFonctionnalites;
    }

    public List<RoleFonctionnalite> getRoleFonctionnalites1() {
        return roleFonctionnalites1;
    }

    public void setRoleFonctionnalites1(List<RoleFonctionnalite> roleFonctionnalites1) {
        this.roleFonctionnalites1 = roleFonctionnalites1;
    }

}
