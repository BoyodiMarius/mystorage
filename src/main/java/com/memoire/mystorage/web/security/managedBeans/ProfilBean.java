/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.log4j.Level;
import org.omnifaces.util.Ajax;

/**
 *
 * @author NOAMESSI
 */
@Named(value = "profilBean")
@ViewScoped
public class ProfilBean implements Serializable {

    private Profil profil;
    private List<Profil> profils;
    private List<Profil> profilsFilter;
    private boolean visible;
    private String space = " ";
    private MethodeJournalisation journalisation;

    @EJB
    private ProfilServiceBeanLocal psbl;

    /**
     * Creates a new instance of CategoriePersonnelBean
     */
    public ProfilBean() {
        this.profils = new ArrayList<>();
        this.profil = new Profil();
        this.journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        try {
            if (LoginBean.currentSubject().hasRole("Consulter Profil") || LoginBean.currentSubject().hasRole("AJouter Profil")
                    || LoginBean.currentSubject().hasRole("Modifier Profil") || LoginBean.currentSubject().hasRole("Consulter Profil-Utilisateur")
                    || LoginBean.currentSubject().hasRole("Associer Profil-Utilisateur") || LoginBean.currentSubject().hasRole("Désactiver Profil-Utilisateur")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(ProfilBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  profil.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }

        } catch (Exception e) {
        }
    }

    public void cancel() {
        try {
            this.profil = new Profil();
        } catch (Exception e) {
        }
    }

    public void cancelProfil() {
        try {
            this.profil = new Profil();
        } catch (Exception e) {
        }
    }

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.profil.getId() == null) {
                List<Profil> list = this.psbl.getBy("libelle", this.profil.getLibelle());
                if (list.isEmpty()) {
                    this.profil.setDateCreation(new Date());
                    this.profil.setUtilisateurAction(LoginBean.currentPersonnel());
                    this.psbl.saveOne(this.profil);
                    journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Enregistrement d'un profil :" + this.profil.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                } else {
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));
                }
            } else {
                List<Profil> list = this.psbl.getBy("libelle", this.profil.getLibelle());
                if (list.isEmpty()) {
                    this.profil.setDateDerniereModification(new Date());
                    this.psbl.updateOne(this.profil);
                    journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Modification d'un profil :" + this.profil.getLibelle());
                    context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
                } else {
                    for (Profil p : list) {
                        if (p.equals(this.profil)) {
                            this.profil.setDateDerniereModification(new Date());
                            this.psbl.updateOne(this.profil);
                            journalisation.saveLog4j(ModulesBean.class.getName(), Level.INFO, "Modification d'un profil :" + this.profil.getLibelle());
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));

                        } else {
                            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE + ", Le libellé saisi existe déja dans la base"));

                        }
                    }
                }
            }
            this.profil = new Profil();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }

    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Profil p = (Profil) value;
        return p.getId().toString().contains(filterText)
                || p.getLibelle().toLowerCase().contains(filterText)
                || p.getDescription().toLowerCase().contains(filterText);
    }

    public void getObject(Long id) {
        try {
            this.profil = this.psbl.find(id);
        } catch (Exception e) {
        }
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Profil> getProfils() {
        try {
            this.profils = this.psbl.getAll();
        } catch (Exception e) {
        }
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public List<Profil> getProfilsFilter() {
        return profilsFilter;
    }

    public void setProfilsFilter(List<Profil> profilsFilter) {
        this.profilsFilter = profilsFilter;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

}
