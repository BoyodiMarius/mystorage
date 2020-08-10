/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;


import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.ProfilUtilisateurServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.project.resultview.web.utils.Constante;
import com.project.resultview.web.utils.MethodeJournalisation;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "compteBean")
@ViewScoped
public class CompteBean implements Serializable {

    private List<Utilisateur> utilisateursFilter;
    private String space = "  ";
    private boolean visible;
    private MethodeJournalisation journalisation;

    @EJB
    private ProfilUtilisateurServiceBeanLocal pusbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;

    public CompteBean() {
        this.journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        try {
            if (LoginBean.currentSubject().hasRole("Activer Compte") || LoginBean.currentSubject().hasRole("Désactiver Compte")
                    || LoginBean.currentSubject().hasRole("Consulter Compte")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(CompteBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  compte.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }

        } catch (Exception e) {
        }
    }

    public List<Utilisateur> utilisateurInactif() {
        List<Utilisateur> us1 = new ArrayList<>();
        try {
            List<Utilisateur> us = this.pusbl.getUtilisateursNonProfil();
            for (Utilisateur us11 : us) {
                if (us11.getProfilactif() == false) {
                    us1.add(us11);
                }
            }
        } catch (Exception e) {
        }
        return us1;
    }

    public List<Utilisateur> utilisateurActif() {
        List<Utilisateur> us1 = new ArrayList<>();
        try {
            List<Utilisateur> us = this.pusbl.getUtilisateursNonProfil();
            for (Utilisateur us11 : us) {
                if (us11.getProfilactif() == true) {
                    us1.add(us11);
                }
            }
        } catch (Exception e) {
        }
        return us1;
    }

    public void activerUtilisateur(Long u) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u1 = new Utilisateur();
            u1 = us.get(0);
            u1.setProfilactif(true);
            this.usbl.updateOne(u1);
            journalisation.saveLog4j(CompteBean.class.getName(), Level.INFO, "Activation de l'utilisateur:" + u1.getPersonnePattern().getNom()+ " " + u1.getPersonnePattern().getPrenom());
            context.addMessage(null, new FacesMessage(u1.getLogin().concat(space) + "activé"));
            u1 = new Utilisateur();
            context.addMessage(null, new FacesMessage(Constante.ACTIVATION_REUSSI));
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ACTIVATION_ECHOU));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }
    }

    public void desactiverUtilisateur(Long u) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u1 = new Utilisateur();
            u1 = us.get(0);
            u1.setProfilactif(false);
            this.usbl.updateOne(u1);
            journalisation.saveLog4j(CompteBean.class.getName(), Level.INFO, "Désactivation de l'utilisateur:" + u1.getPersonnePattern().getNom()+ " " + u1.getPersonnePattern().getPrenom());
            context.addMessage(null, new FacesMessage(u1.getLogin().concat(space) + "désactivé"));
            u1 = new Utilisateur();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_REUSSI));
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DESACTIVATION_ECHOU));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(CompteBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }
    }
 public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

            Utilisateur u = (Utilisateur) value;
        return u.getPersonnePattern().getNom().toLowerCase().contains(filterText)
                || u.getPersonnePattern().getPrenom().toLowerCase().contains(filterText)
                || u.getLogin().toLowerCase().contains(filterText);
    }
    public List<Utilisateur> getUtilisateursFilter() {
        return utilisateursFilter;
    }

    public void setUtilisateursFilter(List<Utilisateur> utilisateursFilter) {
        this.utilisateursFilter = utilisateursFilter;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
