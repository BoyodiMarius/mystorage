/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.PersonnePattern;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.PersonnePatternServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilUtilisateurServiceBeanLocal;
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
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Administrateur
 */
@Named(value = "utilisateurBean")
@ViewScoped
public class UtilisateurBean implements Serializable {

    private Utilisateur utilisateur;
    private PersonnePattern personnePattern;
    private Profil profil;
    private ProfilUtilisateur profilUtilisateur;
    private Utilisateur utilisateur1;
    private List<Utilisateur> utilisateurs;
    private List<Profil> profils;
    private List<ProfilUtilisateur> profilUtilisateurs;
    private List<Utilisateur> utilisateurFilter;
    private List<ProfilUtilisateur> ProfilUtilisateurFilter;

    private boolean visible;

    private MethodeJournalisation journalisation;

    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private ProfilUtilisateurServiceBeanLocal pusbl;
    @EJB
    private PersonnePatternServiceBeanLocal ppsbl;

    /**
     * Creates a new instance of UtilisateurBean
     */
    public UtilisateurBean() {
        this.utilisateur = new Utilisateur();
        this.profil = new Profil();
        this.utilisateur1 = new Utilisateur();
        this.personnePattern = new PersonnePattern();
        this.journalisation = new MethodeJournalisation();
        this.utilisateurs = new ArrayList<>();
        this.profilUtilisateur = new ProfilUtilisateur();
        this.profilUtilisateurs = new ArrayList<>();
        this.profils = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            this.profils = this.psbl.getAll();
            if (LoginBean.currentSubject().hasRole("Consulter Utilisateur") || LoginBean.currentSubject().hasRole("Ajouter Utilisateur")
                    || LoginBean.currentSubject().hasRole("Modifier Utilisateur")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  utilisateurs.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");

            }
        } catch (Exception e) {
        }
    }

    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            if (this.profilUtilisateur.getId() == null) {
                this.personnePattern.setDateCreation(new Date());
                this.personnePattern.setDateDerniereModification(new Date());
                this.ppsbl.saveOne(this.personnePattern);

                this.utilisateur.setPersonnePattern(this.personnePattern);
                this.utilisateur.setDateDerniereModification(new Date());
                this.utilisateur.setProfilactif(false);
                this.utilisateur.setUtilisateurAction(LoginBean.currentPersonnel());
                this.utilisateur.setLogin(this.utilisateur.getPersonnePattern().getPrenom().toLowerCase().charAt(0) + "." + this.utilisateur.getPersonnePattern().getNom().toLowerCase());
                if (usbl.getBy("login", utilisateur.getLogin()).isEmpty()) {
                    this.utilisateur.setPasswd(new Sha256Hash(Constante.MOT_DE_PASSE_DEFAUT).toHex());
                    this.usbl.saveOne(this.utilisateur);
                    journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Enregistrement d'un personnel :" + this.utilisateur.getPersonnePattern().getNom() + " " + this.utilisateur.getPersonnePattern().getPrenom());
                    this.journalisation = new MethodeJournalisation();
                } else {
                    int test = 1;
                    do {
                        test++;
                    } while (usbl.getBy("login", utilisateur.getLogin() + "" + test).isEmpty() == false);
                    utilisateur.setPasswd(new Sha256Hash(Constante.MOT_DE_PASSE_DEFAUT).toHex());
                    utilisateur.setLogin(utilisateur.getLogin() + "" + test);
                    usbl.saveOne(utilisateur);
                }
                this.profilUtilisateur.setUtilisateurProfil(this.utilisateur);
                this.profilUtilisateur.setProfil(this.profil);
                this.profilUtilisateur.setDateAffectation(new Date());
                this.profilUtilisateur.setDateCreation(new Date());
                this.profilUtilisateur.setDateDerniereModification(new Date());
                this.profilUtilisateur.setEtat(true);
                this.pusbl.saveOne(this.profilUtilisateur);
                journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Enregistrement d'un personnel :" + this.utilisateur.getPersonnePattern().getNom() + " " + this.utilisateur.getPersonnePattern().getPrenom());
                this.journalisation = new MethodeJournalisation();
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
            } else {
                this.ppsbl.updateOne(this.personnePattern);
                this.utilisateur = this.profilUtilisateur.getUtilisateurProfil();
                this.utilisateur.setPersonnePattern(this.personnePattern);
                this.usbl.updateOne(this.utilisateur);
                if (this.profilUtilisateur.getProfil().equals(this.profil)) {
                    this.profilUtilisateur.setDateDerniereModification(new Date());
                    this.pusbl.updateOne(this.profilUtilisateur);
                } else {
                    this.profilUtilisateur.setDateRevocation(new Date());
                    this.profilUtilisateur.setDateDerniereModification(new Date());
                    this.profilUtilisateur.setEtat(false);
                    this.pusbl.updateOne(this.profilUtilisateur);
                    this.profilUtilisateur = new ProfilUtilisateur();
                    List<ProfilUtilisateur> pus = this.pusbl.getBy("utilisateurProfil", "etat", this.profilUtilisateur.getUtilisateurProfil(), false);
                    if (pus.isEmpty()) {
                        this.profilUtilisateur.setUtilisateurProfil(this.utilisateur);
                        this.profilUtilisateur.setProfil(this.profil);
                        this.profilUtilisateur.setDateAffectation(new Date());
                        this.profilUtilisateur.setDateCreation(new Date());
                        this.profilUtilisateur.setDateDerniereModification(new Date());
                        this.profilUtilisateur.setEtat(true);
                        this.pusbl.saveOne(this.profilUtilisateur);
                    } else {
                        this.profilUtilisateur = pus.get(0);
                        this.profilUtilisateur.setDateRevocation(null);
                        this.profilUtilisateur.setDateDerniereModification(new Date());
                        this.profilUtilisateur.setEtat(true);
                        this.pusbl.updateOne(this.profilUtilisateur);
                    }
                }
                journalisation.saveLog4j(UtilisateurBean.class.getName(), Level.INFO, "Modification d'un personnel :" + this.utilisateur.getPersonnePattern().getNom() + " " + this.utilisateur.getPersonnePattern().getPrenom());
                this.journalisation = new MethodeJournalisation();
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIE));
            }
            this.personnePattern = new PersonnePattern();
            this.utilisateur = new Utilisateur();
            this.profilUtilisateur = new ProfilUtilisateur();
            this.profil = new Profil();
            this.journalisation = new MethodeJournalisation();
            tx.commit();
       } catch (Exception e) {
           e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(UtilisateurBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }
    }


    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        ProfilUtilisateur pu = (ProfilUtilisateur) value;
        return pu.getProfil().getLibelle().toLowerCase().contains(filterText)
                || pu.getUtilisateurProfil().getPersonnePattern().getNom().toLowerCase().contains(filterText)
                || pu.getUtilisateurProfil().getPersonnePattern().getPrenom().toLowerCase().contains(filterText)
                || pu.getUtilisateurProfil().getPersonnePattern().getMail().toLowerCase().contains(filterText)
                || pu.getUtilisateurProfil().getPersonnePattern().getTelephone().toLowerCase().contains(filterText);
    }

    public void cancel() {
        try {
            this.utilisateur = new Utilisateur();
            this.profil = new Profil();
            this.personnePattern = new PersonnePattern();
        } catch (Exception e) {
        }
    }

    public void getUsers(Long id) {
        this.profilUtilisateur = this.pusbl.find(id);
        this.utilisateur = this.profilUtilisateur.getUtilisateurProfil();
        this.profil = this.profilUtilisateur.getProfil();
        this.personnePattern = this.utilisateur.getPersonnePattern();
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public Utilisateur getUtilisateur1() {
        return utilisateur1;
    }

    public void setUtilisateur1(Utilisateur utilisateur1) {
        this.utilisateur1 = utilisateur1;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public List<Utilisateur> getUtilisateurFilter() {
        return utilisateurFilter;
    }

    public void setUtilisateurFilter(List<Utilisateur> utilisateurFilter) {
        this.utilisateurFilter = utilisateurFilter;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public ProfilUtilisateur getProfilUtilisateur() {
        return profilUtilisateur;
    }

    public void setProfilUtilisateur(ProfilUtilisateur profilUtilisateur) {
        this.profilUtilisateur = profilUtilisateur;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public List<ProfilUtilisateur> getProfilUtilisateurs() {
        try {
            this.profilUtilisateurs = this.pusbl.getAll();
        } catch (Exception e) {
        }
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(List<ProfilUtilisateur> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    public List<ProfilUtilisateur> getProfilUtilisateurFilter() {
        return ProfilUtilisateurFilter;
    }

    public void setProfilUtilisateurFilter(List<ProfilUtilisateur> ProfilUtilisateurFilter) {
        this.ProfilUtilisateurFilter = ProfilUtilisateurFilter;
    }

    public PersonnePattern getPersonnePattern() {
        return personnePattern;
    }

    public void setPersonnePattern(PersonnePattern personnePattern) {
        this.personnePattern = personnePattern;
    }

}
