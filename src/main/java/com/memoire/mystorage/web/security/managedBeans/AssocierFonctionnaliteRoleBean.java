/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.FonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleFonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.project.resultview.web.utils.Constante;
import com.project.resultview.web.utils.MethodeJournalisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Administrateur
 */
@Named(value = "associerFonctionnalite")
@ViewScoped
public class AssocierFonctionnaliteRoleBean implements Serializable {

    private Module modules;
    private Fonctionnalite fonctionnalite;
    private Role rolee;
    private RoleFonctionnalite roleFonctionnalite;
    private boolean visible = true;
    private MethodeJournalisation journalisation;

    private List<Module> moduleses;
    private List<Fonctionnalite> fonctionnalites;
    private List<Role> roles;
    private List<Role> roles1;
    private List<Role> rolesFilter;
    private List<RoleFonctionnalite> roleFonctionnalites;
    private List<RoleFonctionnalite> roleFonctionnalitesFilter;
    private List<RoleFonctionnalite> roleFonctionnalites1;
    private List<Utilisateur> utilisateurs;

    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ModuleServiceBeanLocal msbl;
    @EJB
    private FonctionnaliteServiceBeanLocal fsbl;
    @EJB
    private RoleFonctionnaliteServiceBeanLocal rfsbl;
    @EJB
    private RoleServiceBeanLocal rsbl;

    public AssocierFonctionnaliteRoleBean() {
        this.modules = new Module();
        this.fonctionnalite = new Fonctionnalite();
        this.rolee = new Role();
        this.roleFonctionnalite = new RoleFonctionnalite();
        this.journalisation = new MethodeJournalisation();

        this.moduleses = new ArrayList<>();
        this.fonctionnalites = new ArrayList<>();
        this.roleFonctionnalites = new ArrayList<>();
        this.roleFonctionnalites1 = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.roles1 = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            this.moduleses = this.msbl.getAll("libelle", true);
            this.roles = this.rsbl.getRolesFonctionnalites();
            this.roleFonctionnalites = this.rfsbl.getBy("etat", true);

            if (LoginBean.currentSubject().hasRole("Consulter Fonctionnalité-Role") || LoginBean.currentSubject().hasRole("Associer Fonctionnalité-Role")
                    || LoginBean.currentSubject().hasRole("Désactiver Fonctionnalité-Role")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(usersBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  affectationFonctRole.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fonctModule() {
        try {
            System.out.println("fonctionnalites :" + fonctionnalites);
            this.fonctionnalites = this.fsbl.getModulesFonctionnalites(this.modules);
            System.out.println("fonctionnalites :" + fonctionnalites);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    public void roleFonctionnalite() {
        try {

        } catch (Exception e) {
        }
        this.roles = this.rsbl.getRolesFonctionnalites();

    }

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for (Role role1 : roles1) {
                this.roleFonctionnalites1 = this.rfsbl.getBy("role", "fonctionnalite", role1, this.fonctionnalite);
                if (this.roleFonctionnalites1.isEmpty()) {
                    this.roleFonctionnalite.setDateAffectation(new Date());
                    this.roleFonctionnalite.setDateCreation(new Date());
                    this.roleFonctionnalite.setEtat(true);
                    this.roleFonctionnalite.setRole(role1);
                    this.roleFonctionnalite.setFonctionnalite(this.fonctionnalite);
                    this.roleFonctionnalite.setUtilisateurAction(LoginBean.currentPersonnel());
                    this.rfsbl.saveOne(this.roleFonctionnalite);
                    this.roleFonctionnalites.add(this.roleFonctionnalite);
                    journalisation.saveLog4j(AssocierFonctionnaliteRoleBean.class.getName(), Level.INFO, "Affectation d'une fonctionnalite :" + this.fonctionnalite.getLibelle() + " au role " + role1.getNom());
                    this.roleFonctionnalite = new RoleFonctionnalite();
                } else if (!this.roleFonctionnalites1.isEmpty() && this.roleFonctionnalites1.get(0).getEtat().equals(false)) {
                    this.roleFonctionnalite = this.roleFonctionnalites1.get(0);
                    this.roleFonctionnalite.setEtat(true);
                    this.roleFonctionnalite.setDateRevocation(null);
                    this.roleFonctionnalite.setDateDerniereModification(new Date());
                    this.rfsbl.updateOne(this.roleFonctionnalite);
                    this.roleFonctionnalites.add(this.roleFonctionnalite);
                    journalisation.saveLog4j(AssocierFonctionnaliteRoleBean.class.getName(), Level.INFO, "Modification d'une fonctionnalite :" + this.fonctionnalite.getLibelle() + " au role " + role1.getNom());
                    this.roleFonctionnalite = new RoleFonctionnalite();
                }

                this.roleFonctionnalites1 = new ArrayList<>();
            }
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
            this.fonctionnalite = new Fonctionnalite();
            this.modules = new Module();
            this.roles = new ArrayList<>();
            this.roles1 = new ArrayList<>();
            this.roleFonctionnalite = new RoleFonctionnalite();
        } catch (Exception e) {

        }
    }

    public void cancel() {
        try {
            this.fonctionnalite = new Fonctionnalite();
            this.modules = new Module();
            this.roleFonctionnalite = new RoleFonctionnalite();
        } catch (Exception e) {
        }
    }

    public void unluckFonc(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            this.roleFonctionnalite = this.rfsbl.find(id);
            this.roleFonctionnalite.setDateDerniereModification(new Date());
            this.roleFonctionnalite.setDateRevocation(new Date());
            this.roleFonctionnalite.setEtat(false);
            this.rfsbl.updateOne(this.roleFonctionnalite);
            this.roleFonctionnalites.remove(this.roleFonctionnalite);
            journalisation.saveLog4j(AssocierFonctionnaliteRoleBean.class.getName(), Level.INFO, "Modification de la fonctionnalité :" + this.roleFonctionnalite.getFonctionnalite().getLibelle() + " au role " + this.roleFonctionnalite.getRole().getNom());
            this.roleFonctionnalite = new RoleFonctionnalite();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_REUSSIE));
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_REUSSIE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(AssocierFonctionnaliteRoleBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(AssocierFonctionnaliteRoleBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(AssocierFonctionnaliteRoleBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }

    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        RoleFonctionnalite rf = (RoleFonctionnalite) value;
        return rf.getId().toString().contains(filterText)
                || rf.getFonctionnalite().getLibelle().toLowerCase().contains(filterText)
                || rf.getRole().getNom().toLowerCase().contains(filterText);
    }

    public String message(Long id) {
        String message = "";
        try {
            this.roleFonctionnalite = this.rfsbl.find(id);
            message = "Voulez vous modifier la fonctionnalité :" + this.roleFonctionnalite.getFonctionnalite().getLibelle() + " pour le role :" + this.roleFonctionnalite.getRole().getNom() + " ?";
        } catch (Exception e) {
        }
        return message;
    }

    public Module getModules() {
        return modules;
    }

    public void setModules(Module modules) {
        this.modules = modules;
    }

    public Fonctionnalite getFonctionnalite() {
        return fonctionnalite;
    }

    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

    public Role getRolee() {
        return rolee;
    }

    public void setRolee(Role rolee) {
        this.rolee = rolee;
    }

    public RoleFonctionnalite getRoleFonctionnalite() {
        return roleFonctionnalite;
    }

    public void setRoleFonctionnalite(RoleFonctionnalite roleFonctionnalite) {
        this.roleFonctionnalite = roleFonctionnalite;
    }

    public List<Module> getModuleses() {
        return moduleses;
    }

    public void setModuleses(List<Module> moduleses) {
        this.moduleses = moduleses;
    }

    public List<Fonctionnalite> getFonctionnalites() {
        return fonctionnalites;
    }

    public void setFonctionnalites(List<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles1() {
        return roles1;
    }

    public void setRoles1(List<Role> roles1) {
        this.roles1 = roles1;
    }

    public List<Role> getRolesFilter() {
        return rolesFilter;
    }

    public void setRolesFilter(List<Role> rolesFilter) {
        this.rolesFilter = rolesFilter;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<RoleFonctionnalite> getRoleFonctionnalitesFilter() {
        return roleFonctionnalitesFilter;
    }

    public void setRoleFonctionnalitesFilter(List<RoleFonctionnalite> roleFonctionnalitesFilter) {
        this.roleFonctionnalitesFilter = roleFonctionnalitesFilter;
    }

}
