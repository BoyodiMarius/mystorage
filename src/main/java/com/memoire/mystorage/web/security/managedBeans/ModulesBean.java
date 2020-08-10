/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
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
import org.apache.log4j.Level;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Administrateur
 */
@Named(value = "modulesBean")
@ViewScoped
public class ModulesBean implements Serializable {

    private Module modules;
    private List<Module> moduleses;
    private List<Module> modulesesFilter;
    private boolean visible = true;
    private MethodeJournalisation journalisation;
    private List<Utilisateur> utilisateurs;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ModuleServiceBeanLocal msbl;

    public ModulesBean() {
        this.modules = new Module();
        this.moduleses = new ArrayList<>();
        this.journalisation = new MethodeJournalisation();
        this.utilisateurs = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            if (LoginBean.currentSubject().hasRole("Consulter Module") || LoginBean.currentSubject().hasRole("Ajouter Module")
                    || LoginBean.currentSubject().hasRole("Modifier Module")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(CompteBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  gesModule.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        try {
            System.out.println("Testt");
            this.modules = new Module();
        } catch (Exception e) {
        }
    }

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.modules.getId() == null) {
                List<Module> ms = this.msbl.getBy("libelle", this.modules.getLibelle());
                if (ms.isEmpty()) {
                    this.modules.setDateCreation(new Date());
                    this.modules.setUtilisateurAction(LoginBean.currentPersonnel());
                    this.msbl.saveOne(this.modules);
                    journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Enregistrement d'un module :" + this.modules.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                } else {
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));
                }
            } else {
                List<Module> ms = this.msbl.getBy("libelle", this.modules.getLibelle());
                if (ms.isEmpty()) {
                    this.modules.setDateDerniereModification(new Date());
                    this.msbl.updateOne(this.modules);
                    journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Modification d'un module :" + this.modules.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                } else {
                    for (Module m : ms) {
                        if (m.equals(this.modules)) {
                            this.modules.setDateDerniereModification(new Date());
                            this.msbl.updateOne(this.modules);
                            journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Modification d'un module :" + this.modules.getLibelle());
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));

                        } else {
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));

                        }
                    }
                }
            }
            this.modules = new Module();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }

    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Module m = (Module) value;
        return m.getId().toString().contains(filterText)
                || m.getLibelle().toLowerCase().contains(filterText)
                || m.getDescription().toLowerCase().contains(filterText);
    }

    public void getObject(Long id) {
        try {
            this.modules = this.msbl.find(id);
        } catch (Exception e) {
        }
    }

    public Module getModules() {
        return modules;
    }

    public void setModules(Module modules) {
        this.modules = modules;
    }

    public List<Module> getModuleses() {
        try {
            this.moduleses = this.msbl.getAll().stream()
                    .sorted(Comparator.comparing(Module::getLibelle))
                    .collect(Collectors.toList());
        } catch (Exception e) {
        }
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

    public List<Module> getModulesesFilter() {
        return modulesesFilter;
    }

    public void setModulesesFilter(List<Module> modulesesFilter) {
        this.modulesesFilter = modulesesFilter;
    }

}
