/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.FonctionnaliteModule;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.FonctionnaliteModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.FonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.project.resultview.web.utils.Constante;
import com.project.resultview.web.utils.MethodeJournalisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
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
@Named(value = "fonctionnaliteBean")
@ViewScoped
public class FonctionnaliteBean implements Serializable {

    private FonctionnaliteModule fonctionnaliteModule;
    private FonctionnaliteModule fonctionnaliteModule1;
    private List<FonctionnaliteModule> fonctionnaliteModules;
    private List<FonctionnaliteModule> fonctionnaliteModulesFilter;
    private Fonctionnalite fonctionnalite;
    private List<Fonctionnalite> fonctionnalites1;
    private List<Fonctionnalite> fonctionnalites2;
    private List<Fonctionnalite> fonctionnalites;
    private List<Fonctionnalite> fonctionnalitesFilter;
    private Module modules;
    private List<Module> moduleses;
    private boolean visible = true;
    private MethodeJournalisation journalisation;
    private List<Utilisateur> utilisateurs;
    @EJB
    private FonctionnaliteServiceBeanLocal fsbl;
    @EJB
    private FonctionnaliteModuleServiceBeanLocal fmsbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ModuleServiceBeanLocal msbl;

    public FonctionnaliteBean() {
        this.fonctionnaliteModule = new FonctionnaliteModule();
        this.fonctionnaliteModule1 = new FonctionnaliteModule();
        this.fonctionnaliteModules = new ArrayList<>();
        this.fonctionnalite = new Fonctionnalite();
        this.fonctionnalites = new ArrayList<>();
        this.fonctionnalites1 = new ArrayList<>();
        this.fonctionnalites2 = new ArrayList<>();
        this.modules = new Module();
        this.moduleses = new ArrayList<>();
        this.journalisation = new MethodeJournalisation();
        this.utilisateurs = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            this.moduleses = this.msbl.getAll("libelle", true);
            this.fonctionnaliteModules = this.fmsbl.getBy("etat", true);
            this.fonctionnalites2 = this.fsbl.getAll();
            if (LoginBean.currentSubject().hasRole("Consulter Fonctionnalité") || LoginBean.currentSubject().hasRole("Ajouter Fonctionnalité")
                    || LoginBean.currentSubject().hasRole("Modifier Fonctionnalité") || LoginBean.currentSubject().hasRole("Consulter Fonctionnalité-Module")
                    || LoginBean.currentSubject().hasRole("Associer Fonctionnalité-Module") || LoginBean.currentSubject().hasRole("Désactiver Fonctionnalité-Module")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(CompteBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  gesModule.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }
        } catch (Exception e) {
        }
    }

    public void cancel() {
        try {
            this.fonctionnalite = new Fonctionnalite();
            this.modules = new Module();
            this.fonctionnaliteModule = new FonctionnaliteModule();
        } catch (Exception e) {
        }
    }

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.fonctionnalite.getId() == null) {
                List<Fonctionnalite> fs = this.fsbl.getBy("libelle", this.fonctionnalite.getLibelle());
                if (fs.isEmpty()) {
                    this.fonctionnalite.setDateCreation(new Date());
                    this.fonctionnalite.setUtilisateurAction(LoginBean.currentPersonnel());
                    this.fsbl.saveOne(this.fonctionnalite);
                    journalisation.saveLog4j(FonctionnaliteBean.class.getName(), Level.INFO, "Enregistrement d'une fonctionnalité :" + this.fonctionnalite.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                    this.fonctionnalite = new Fonctionnalite();
                } else {
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));
                }
            } else {
                List<Fonctionnalite> fs = this.fsbl.getBy("libelle", this.fonctionnalite.getLibelle());
                if (fs.isEmpty()) {
                    this.fonctionnalite.setDateDerniereModification(new Date());
                    this.fsbl.updateOne(this.fonctionnalite);
                    journalisation.saveLog4j(FonctionnaliteBean.class.getName(), Level.INFO, "Modification d'une fonctionnalité :" + this.fonctionnalite.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                } else {
                    for (Fonctionnalite f1 : fs) {
                        if (f1.equals(fonctionnalite)) {
                            this.fonctionnalite.setDateDerniereModification(new Date());
                            this.fsbl.updateOne(this.fonctionnalite);
                            journalisation.saveLog4j(FonctionnaliteBean.class.getName(), Level.INFO, "Modification d'une fonctionnalité :" + this.fonctionnalite.getLibelle());
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                        } else {
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));
                        }
                    }
                }
            }
            this.fonctionnalite = new Fonctionnalite();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }

    }

    public void associerModule() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            for (Fonctionnalite fonctionnalite1 : fonctionnalites1) {
                List<FonctionnaliteModule> fms = this.fmsbl.getBy("fonctionnalite", "modules", "etat", fonctionnalite1, this.modules, false);
                if (fms.isEmpty()) {
                    this.fonctionnaliteModule.setModules(this.modules);
                    this.fonctionnaliteModule.setFonctionnalite(fonctionnalite1);
                    this.fonctionnaliteModule.setDateCreation(new Date());
                    this.fonctionnaliteModule.setDateAffectation(new Date());
                    this.fonctionnaliteModule.setEtat(true);
                    this.fmsbl.saveOne(fonctionnaliteModule);
                    this.fonctionnaliteModules.add(fonctionnaliteModule);
                    journalisation.saveLog4j(FonctionnaliteBean.class.getName(), Level.INFO, "Affectation du module:" + this.modules.getLibelle() + " à la fonctionnalité :" + fonctionnalite1.getLibelle());
                    this.fonctionnaliteModule = new FonctionnaliteModule();
                } else {
                    this.fonctionnaliteModule = fms.get(0);
                    this.fonctionnaliteModule.setEtat(true);
                    this.fonctionnaliteModule.setDateDerniereModification(new Date());
                    this.fonctionnaliteModule.setDateRevocation(null);
                    this.fmsbl.updateOne(this.fonctionnaliteModule);
                    this.fonctionnaliteModules.add(this.fonctionnaliteModule);
                    journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Modification du module:" + this.fonctionnaliteModule.getModules().getLibelle() + " à la fonctionnalité :" + this.fonctionnaliteModule.getFonctionnalite().getLibelle());

                }
            }
            this.fonctionnalites1 = new ArrayList<>();
            this.fonctionnaliteModule = new FonctionnaliteModule();
            this.modules = new Module();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
            tx.commit();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }
    }

    public void modifierAssocierModules() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            if (this.fonctionnalite != null) {
                List<FonctionnaliteModule> fms = this.fmsbl.getBy("fonctionnalite", this.fonctionnalite);
                if (fms.isEmpty()) {
                    this.fonctionnaliteModule1.setModules(this.modules);
                    this.fonctionnaliteModule1.setFonctionnalite(this.fonctionnalite);
                    this.fonctionnaliteModule1.setDateCreation(new Date());
                    this.fonctionnaliteModule1.setDateAffectation(new Date());
                    this.fonctionnaliteModule1.setEtat(true);
                    this.fmsbl.saveOne(fonctionnaliteModule1);
                    journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Affectation du module:" + this.modules.getLibelle() + " à la fonctionnalité :" + this.fonctionnalite.getLibelle());

                    this.modules = new Module();
                    this.fonctionnalite = new Fonctionnalite();
                    this.fonctionnaliteModule1 = new FonctionnaliteModule();
                } else {
                    for (FonctionnaliteModule fm : fms) {
                        if (fm.getModules() != this.modules && fm.getEtat() == true) {
                            this.fonctionnaliteModule.setEtat(false);
                            this.fonctionnaliteModule.setDateDerniereModification(new Date());
                            this.fonctionnaliteModule.setDateRevocation(new Date());
                            this.fmsbl.updateOne(this.fonctionnaliteModule);
                            this.fonctionnaliteModules.remove(this.fonctionnaliteModule);
                            journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Modification du module:" + this.fonctionnaliteModule.getModules().getLibelle() + " à la fonctionnalité :" + this.fonctionnaliteModule.getFonctionnalite().getLibelle());

                            this.fonctionnaliteModule1.setModules(this.modules);
                            this.fonctionnaliteModule1.setFonctionnalite(this.fonctionnalite);
                            this.fonctionnaliteModule1.setDateCreation(new Date());
                            this.fonctionnaliteModule1.setDateAffectation(new Date());
                            this.fonctionnaliteModule1.setEtat(true);
                            this.fmsbl.saveOne(fonctionnaliteModule1);
                            this.fonctionnaliteModules.add(this.fonctionnaliteModule1);
                            journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Affectation du module:" + this.modules.getLibelle() + " à la fonctionnalité :" + this.fonctionnalite.getLibelle());

                            this.modules = new Module();
                            this.fonctionnalite = new Fonctionnalite();
                            this.fonctionnaliteModule = new FonctionnaliteModule();
                            this.fonctionnaliteModule1 = new FonctionnaliteModule();
                        }

                        if (fm.getModules() == this.modules && fm.getEtat() == false) {
                            this.fonctionnaliteModule.setEtat(false);
                            this.fonctionnaliteModule.setDateDerniereModification(new Date());
                            this.fonctionnaliteModule.setDateRevocation(new Date());
                            this.fmsbl.updateOne(this.fonctionnaliteModule);
                            this.fonctionnaliteModules.remove(this.fonctionnaliteModule);
                            journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Modification du module:" + this.fonctionnaliteModule.getModules().getLibelle() + " à la fonctionnalité :" + this.fonctionnaliteModule.getFonctionnalite().getLibelle());

                            this.fonctionnaliteModule1 = fm;
                            this.fonctionnaliteModule1.setEtat(true);
                            this.fonctionnaliteModule1.setDateDerniereModification(new Date());
                            this.fonctionnaliteModule1.setDateRevocation(null);
                            this.fmsbl.updateOne(fonctionnaliteModule1);
                            this.fonctionnaliteModules.add(this.fonctionnaliteModule1);
                            journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Modification du module:" + this.fonctionnaliteModule1.getModules().getLibelle() + " à la fonctionnalité :" + this.fonctionnaliteModule1.getFonctionnalite().getLibelle());

                            this.modules = new Module();
                            this.fonctionnalite = new Fonctionnalite();
                            this.fonctionnaliteModule = new FonctionnaliteModule();
                            this.fonctionnaliteModule1 = new FonctionnaliteModule();
                        }
                    }
                }
            }
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(FonctionnaliteBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }

    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Fonctionnalite f = (Fonctionnalite) value;
        return f.getId().toString().contains(filterText)
                || f.getLibelle().toLowerCase().contains(filterText)
                || f.getDescription().toLowerCase().contains(filterText);
    }

    public boolean globalFilterFunction1(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        FonctionnaliteModule fm = (FonctionnaliteModule) value;
        return fm.getId().toString().contains(filterText)
                || fm.getModules().getLibelle().toLowerCase().contains(filterText)
                || fm.getFonctionnalite().getLibelle().toLowerCase().contains(filterText);
    }

    public List<Fonctionnalite> fonctionnaliteNonModules() {
        return this.fsbl.getFonctionnalitesModules();
    }

    public void unluckFonc(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            this.fonctionnaliteModule = this.fmsbl.find(id);
            this.fonctionnaliteModule.setDateDerniereModification(new Date());
            this.fonctionnaliteModule.setDateRevocation(new Date());
            this.fonctionnaliteModule.setEtat(false);
            this.fmsbl.updateOne(this.fonctionnaliteModule);
            this.fonctionnaliteModules.remove(this.fonctionnaliteModule);
            journalisation.saveLog4j(AssocierFonctionnaliteRoleBean.class.getName(), Level.INFO, "Modification de la fonctionnalité :" + this.fonctionnaliteModule.getFonctionnalite().getLibelle() + " au module " + this.fonctionnaliteModule.getModules().getLibelle());
            this.fonctionnaliteModule = new FonctionnaliteModule();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_REUSSIE));
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_ECHOUE));
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


    public void getObject(Long id) {
        try {
            this.fonctionnaliteModule = this.fmsbl.find(id);
            if (this.fonctionnaliteModule.getEtat().equals(true)) {
                if (this.fonctionnaliteModule.getModules() != null) {
                    this.modules = this.fonctionnaliteModule.getModules();
                }

                if (this.fonctionnaliteModule.getFonctionnalite() != null) {
                    this.fonctionnalite = this.fonctionnaliteModule.getFonctionnalite();
                }
            }

        } catch (Exception e) {
        }
    }

    public void getObjectFonctionnalite(Long id) {
        try {
            this.fonctionnalite = this.fsbl.find(id);

        } catch (Exception e) {
        }
    }

    public Fonctionnalite getFonctionnalite() {
        return fonctionnalite;
    }

    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

    public List<Fonctionnalite> getFonctionnalites() {
        try {
            this.fonctionnalites = this.fsbl.getAll().stream()
                    .sorted(Comparator.comparing(Fonctionnalite::getLibelle))
                    .collect(Collectors.toList());
        } catch (Exception e) {
        }
        return fonctionnalites;
    }

    public void setFonctionnalites(List<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }

    public List<Fonctionnalite> getFonctionnalitesFilter() {
        return fonctionnalitesFilter;
    }

    public void setFonctionnalitesFilter(List<Fonctionnalite> fonctionnalitesFilter) {
        this.fonctionnalitesFilter = fonctionnalitesFilter;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public FonctionnaliteModule getFonctionnaliteModule() {
        return fonctionnaliteModule;
    }

    public void setFonctionnaliteModule(FonctionnaliteModule fonctionnaliteModule) {
        this.fonctionnaliteModule = fonctionnaliteModule;
    }

    public List<FonctionnaliteModule> getFonctionnaliteModules() {
        return fonctionnaliteModules;
    }

    public void setFonctionnaliteModules(List<FonctionnaliteModule> fonctionnaliteModules) {
        this.fonctionnaliteModules = fonctionnaliteModules;
    }

    public List<FonctionnaliteModule> getFonctionnaliteModulesFilter() {
        return fonctionnaliteModulesFilter;
    }

    public void setFonctionnaliteModulesFilter(List<FonctionnaliteModule> fonctionnaliteModulesFilter) {
        this.fonctionnaliteModulesFilter = fonctionnaliteModulesFilter;
    }

    public List<Fonctionnalite> getFonctionnalites1() {
        return fonctionnalites1;
    }

    public void setFonctionnalites1(List<Fonctionnalite> fonctionnalites1) {
        this.fonctionnalites1 = fonctionnalites1;
    }

    public List<Fonctionnalite> getFonctionnalites2() {
        return fonctionnalites2;
    }

    public void setFonctionnalites2(List<Fonctionnalite> fonctionnalites2) {
        this.fonctionnalites2 = fonctionnalites2;
    }

}
