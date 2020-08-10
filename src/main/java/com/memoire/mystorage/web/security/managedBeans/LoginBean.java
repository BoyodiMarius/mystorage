/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.FonctionnaliteModule;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.PersonnePattern;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.FonctionnaliteModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.FonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.PersonnePatternServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilUtilisateurServiceBeanLocal;
import com.memoire.mystorage.api.service.security.RoleServiceBeanLocal;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.project.resultview.web.utils.Constante;
import com.project.resultview.web.utils.MethodeJournalisation;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;

/**
 *
 * @author Brendev
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private MethodeJournalisation journalisation;
    private Utilisateur pers;
    private Utilisateur perse;
    private PersonnePattern personnePattern;
    private ProfilUtilisateur profilUtilisateur;
    private ProfilRole profilRole;
    private static Utilisateur curentUsers;
    private List<ProfilUtilisateur> profilUtilisateurs;
    private static String ip;
    private static Subject subject;
    private boolean remember = true;
    private boolean admin;
    private String space = " | ";
    private String espace = " ";
    @EJB
    private RoleServiceBeanLocal rsl;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilUtilisateurServiceBeanLocal pusbl;
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;
    @EJB
    private PersonnePatternServiceBeanLocal ppsbl;
    @EJB
    private ModuleServiceBeanLocal msbl;
    @EJB
    private FonctionnaliteServiceBeanLocal fsbl;
    @EJB
    private FonctionnaliteModuleServiceBeanLocal fmsbl;

    @Inject
    private usersBean bean;

    private Date date = new Date();
    private String curentUser;
    private String tribunal;
    private String username;
    private String password;
    private String newPass;
    private String retapPass;
    private String lastPass;
    private String per = "";
    private String question;
    private String reponse, recupQuestion;

    //-I- TABLEAU DE BORD
    private String tableauSecurite;

    //-II - GESTION DES MODULES
    private String gestionModule;
    //-- A-- Module;
    private String consulterModule, ajouterModule, modifierModule;
    //-- B-- Association Fonctionnalité Module
    private String moduleFonctionnalite;
    //------Fonctionnalité
    private String consulterFonctionnalite, ajouterFonctionnalite, modifierFonctionnalite;
    //------Associer Fonctionnalite Module
    private String consulterFModule, associerFModule, desactiverFModule;

    //-III- ASSOCITION FONCTIONALITE ROLE PROFIL
    private String associationFonctionnaliteRole;
    //--A-- FFECTATION fONCTIONNALITE ROLE
    private String consulterFrole, associerFrole, desactiverFrole;
    //--B-- AFFECTATION PROFIL ROLE
    private String consulterProle, associerPRole;

    //-IV - COMPTES UTILISATEURS
    private String compteUtilisateurs;
    //--A-- UTILISATEURS
    private String consulterUtilisateur, ajouterUtilisateur, modifierUtilisateur;
    //--B-- MODULES UTILISATEURS
    private String consulterMUtilisateur, associerMUtilisateur, desactiverMUtilisateur;
    //--C-- PROFIL uTILISATEUR
    private String associationProfilUtilisateur;
    //------PROFIL
    private String consulterProfil, ajouterProfil, modifierProfil;
    //------Affectation Profil Utilisateur
    private String consulterPUtilisateur, associerPUtilisateur, desactiverPUtilisateur;
    //--D-- COMPTE
    private String consulterCompte, activerCompte, desactiverCompte;

    //-V-UTILISATEURS ET EXTRAS
    private String utilisateursExtras;
    private String consulterHistorique, consulterUtilisateurConnectes;

    public LoginBean() {
        this.pers = new Utilisateur();
        this.perse = new Utilisateur();
        this.personnePattern = new PersonnePattern();
        this.journalisation = new MethodeJournalisation();
        this.bean = new usersBean();
        this.profilUtilisateurs = new ArrayList<>();
        this.profilUtilisateur = new ProfilUtilisateur();
        this.profilRole = new ProfilRole();
    }

    @PostConstruct
    public void init() {
        try {

            UserTransaction tx = TransactionManager.getUserTransaction();
            List<Role> all = rsl.getAll();
            if (all.isEmpty()) {
                this.rsl.saveOne(new Role("Accès Tableau Sécurité"));
                this.rsl.saveOne(new Role("Consulter Module"));
                this.rsl.saveOne(new Role("Consulter Fonctionnalité"));
                this.rsl.saveOne(new Role("Consulter Fonctionnalité-Module"));
                this.rsl.saveOne(new Role("Ajouter Module"));
                this.rsl.saveOne(new Role("Modifier Module"));
                this.rsl.saveOne(new Role("Ajouter Fonctionnalité"));
                this.rsl.saveOne(new Role("Modifier Fonctionnalité"));
                this.rsl.saveOne(new Role("Associer Fonctionnalité-Module"));
                this.rsl.saveOne(new Role("Désactiver Fonctionnalité-Module"));
                this.rsl.saveOne(new Role("Associer Fonctionnalité-Role"));
                this.rsl.saveOne(new Role("Désactiver Fonctionnalité-Role"));
                this.rsl.saveOne(new Role("Associer Profil-Role"));
                this.rsl.saveOne(new Role("Consulter Fonctionnalité-Role"));
                this.rsl.saveOne(new Role("Consulter Profil-Role"));
                this.rsl.saveOne(new Role("Consulter Utilisateur"));
                this.rsl.saveOne(new Role("Ajouter Utilisateur"));
                this.rsl.saveOne(new Role("Modifier Utilisateur"));
                this.rsl.saveOne(new Role("Consulter Profil"));
                this.rsl.saveOne(new Role("AJouter Profil"));
                this.rsl.saveOne(new Role("Modifier Profil"));
                this.rsl.saveOne(new Role("Consulter Profil-Utilisateur"));
                this.rsl.saveOne(new Role("Associer Profil-Utilisateur"));
                this.rsl.saveOne(new Role("Désactiver Profil-Utilisateur"));
                this.rsl.saveOne(new Role("Consulter Compte"));
                this.rsl.saveOne(new Role("Activer Compte"));
                this.rsl.saveOne(new Role("Désactiver Compte"));
                this.rsl.saveOne(new Role("Consulter Historique"));
                this.rsl.saveOne(new Role("Consulter Utilisateur Connectés"));
            }

            if (this.msbl.getAll().isEmpty() && this.fsbl.getAll().isEmpty() && this.fmsbl.getAll().isEmpty()) {
                this.msbl.saveOne(new Module("Sécurité", "Gestion de la sécurité"));

                this.fsbl.saveOne(new Fonctionnalite("Modules", "Gestion des modules"));
                this.fsbl.saveOne(new Fonctionnalite("Roles", "Gestion des roles"));
                this.fsbl.saveOne(new Fonctionnalite("Utilisateurs", "Gestion des utilisateurs"));

                this.fmsbl.saveOne(new FonctionnaliteModule(this.msbl.getOneBy("libelle", "Sécurité"), this.fsbl.getOneBy("libelle", "Modules"), true));
                this.fmsbl.saveOne(new FonctionnaliteModule(this.msbl.getOneBy("libelle", "Sécurité"), this.fsbl.getOneBy("libelle", "Roles"), true));
                this.fmsbl.saveOne(new FonctionnaliteModule(this.msbl.getOneBy("libelle", "Sécurité"), this.fsbl.getOneBy("libelle", "Utilisateurs"), true));
            }

            try {
                tx.begin();
                List<Profil> profils = psbl.getBy("libelle", "Admin");
                if (profils.isEmpty()) {
                    this.psbl.saveOne(new Profil("Admin", "Administrateur du système"));
                    List<Role> roles = this.rsl.getAll();
                    for (Role role : roles) {
                        this.profilRole.setRole(role);
                        this.profilRole.setEtat(true);
                        this.profilRole.setProfil(psbl.getOneBy("libelle", "Admin"));
                        prsbl.saveOne(this.profilRole);
                        this.profilRole = new ProfilRole();
                    }

                    this.personnePattern.setNom("security");
                    this.personnePattern.setPrenom("security");
                    this.personnePattern.setMail("security@security.com");
                    this.personnePattern.setTelephone("99656151");
                    this.personnePattern.setSexe("Masculin");
                    this.ppsbl.saveOne(this.personnePattern);

                    this.pers.setLogin("security");
                    this.pers.setQuestion("security");
                    this.pers.setReponse("security");
                    this.pers.setPersonnePattern(this.ppsbl.getBy("telephone", "99656151").get(0));
                    this.pers.setPasswd(new Sha256Hash("@security2020").toHex());
                    this.pers.setProfilactif(true);
                    usbl.saveOne(this.pers);

                    this.profilUtilisateur.setUtilisateurProfil(usbl.getBy("login", "security").get(0));
                    this.profilUtilisateur.setDateAffectation(date);
                    this.profilUtilisateur.setProfil(psbl.getBy("libelle", "Admin").get(0));
                    this.profilUtilisateur.setDateCreation(new Date());
                    this.profilUtilisateur.setDateDerniereModification(new Date());
                    this.profilUtilisateur.setDateAffectation(new Date());
                    this.profilUtilisateur.setEtat(true);
                    pusbl.saveOne(this.profilUtilisateur);
                    this.profilUtilisateur = new ProfilUtilisateur();
                    this.pers = new Utilisateur();
                    this.personnePattern = new PersonnePattern();
                    tx.commit();
                }
            } catch (Exception e) {
                e.getMessage();
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
                }
            }

        } catch (Exception e) {
        }
    }

    public String message() {
        String message = "";
        try {
            message = "Confirmez-vous cette opération ?";
        } catch (Exception e) {
        }
        return message;
    }

    public static List<Utilisateur> us() {
        return (List<Utilisateur>) SecurityUtils.getSubject().getPrincipals();
    }

    public static Utilisateur currentPersonnel() {
        try {
            LoginBean.curentUsers = (Utilisateur) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {
        }
        return LoginBean.curentUsers;
    }

    public static String getHostIp() {
        try {
            LoginBean.ip = SecurityUtils.getSubject().getSession().getHost();
        } catch (Exception e) {
        }
        return ip;
    }

    public String profilUtilisateur() {
        try {
            this.profilUtilisateurs = this.pusbl.getBy("utilisateurProfil", "etat", LoginBean.currentPersonnel(), true);
        } catch (Exception e) {
        }
        return !profilUtilisateurs.isEmpty() ? this.profilUtilisateurs.get(0).getProfil().getLibelle() : "Admin";
    }

    public static Subject currentSubject() {
        try {
            LoginBean.subject = SecurityUtils.getSubject();
        } catch (Exception e) {
        }
        return LoginBean.subject;
    }

    public void login() throws IOException {
        try {
            pers = usbl.getOneBy("login", username);
            if (pers != null) {
                if (pers.getProfilactif() == false) {
                    Faces.redirect("locked.xhtml");
                    username = "";
                    return;
                }
            }

            if (pers != null) {
                boolean test = new Sha256Hash(Constante.MOT_DE_PASSE_DEFAUT).toHex().equals(pers.getPasswd());
                if (test && pers.getProfilactif() == true) {
                    Faces.redirect("change_password.xhtml");
                    return;
                }

            }

            UsernamePasswordToken token = new UsernamePasswordToken(username.trim(), password.trim());
            token.setRememberMe(false);
            SecurityUtils.getSubject().login(token);

            if (!username.equalsIgnoreCase("admin")) {

                //TABLEAU DE BORD
                if (currentSubject().hasRole("Accès Tableau Sécurité")) {
                    this.tableauSecurite = "true";
                } else {
                    this.tableauSecurite = "false";
                }

                //GESTION DE MODULE
                if (currentSubject().hasRole("Consulter Module") || currentSubject().hasRole("Consulter Fonctionnalité")
                        || currentSubject().hasRole("Consulter Fonctionnalité-Module")
                        || currentSubject().hasRole("Ajouter Module") || currentSubject().hasRole("Modifier Module")
                        || currentSubject().hasRole("Ajouter Fonctionnalité") || currentSubject().hasRole("Modifier Fonctionnalité")
                        || currentSubject().hasRole("Associer Fonctionnalité-Module") || currentSubject().hasRole("Désactiver Fonctionnalité-Module")) {
                    this.gestionModule = "true";
                } else {
                    this.gestionModule = "false";
                }

                if (currentSubject().hasRole("Consulter Module") || currentSubject().hasRole("Ajouter Module") || currentSubject().hasRole("Modifier Module")) {
                    this.consulterModule = "true";
                } else {
                    this.consulterModule = "false";
                }

                if (currentSubject().hasRole("Consulter Fonctionnalité") || currentSubject().hasRole("Consulter Fonctionnalité-Module")
                        || currentSubject().hasRole("Ajouter Fonctionnalité") || currentSubject().hasRole("Modifier Fonctionnalité")
                        || currentSubject().hasRole("Associer Fonctionnalité-Module") || currentSubject().hasRole("Désactiver Fonctionnalité-Module")) {
                    this.moduleFonctionnalite = "true";
                } else {
                    this.moduleFonctionnalite = "false";
                }

                if (currentSubject().hasRole("Consulter Fonctionnalité") || currentSubject().hasRole("Ajouter Fonctionnalité") || currentSubject().hasRole("Modifier Fonctionnalité")) {
                    this.consulterFonctionnalite = "true";
                } else {
                    this.consulterFonctionnalite = "false";
                }

                if (currentSubject().hasRole("Consulter Fonctionnalité-Module") || currentSubject().hasRole("Associer Fonctionnalité-Module") || currentSubject().hasRole("Désactiver Fonctionnalité-Module")) {
                    this.consulterFModule = "true";
                } else {
                    this.consulterFModule = "false";
                }

                if (currentSubject().hasRole("Ajouter Module")) {
                    this.ajouterModule = "true";
                } else {
                    this.ajouterModule = "false";
                }

                if (currentSubject().hasRole("Modifier Module")) {
                    this.modifierModule = "true";
                } else {
                    this.modifierModule = "false";
                }

                if (currentSubject().hasRole("Ajouter Fonctionnalité")) {
                    this.ajouterFonctionnalite = "true";
                } else {
                    this.ajouterFonctionnalite = "false";
                }

                if (currentSubject().hasRole("Modifier Fonctionnalité")) {
                    this.modifierFonctionnalite = "true";
                } else {
                    this.modifierFonctionnalite = "false";
                }

                if (currentSubject().hasRole("Associer Fonctionnalité-Module")) {
                    this.associerFModule = "true";
                } else {
                    this.associerFModule = "false";
                }

                if (currentSubject().hasRole("Désactiver Fonctionnalité-Module")) {
                    this.desactiverFModule = "true";
                } else {
                    this.desactiverFModule = "false";
                }

                //GESTION DE ASSOCIATION ROLES
                if (currentSubject().hasRole("Associer Fonctionnalité-Role") || currentSubject().hasRole("Désactiver Fonctionnalité-Role")
                        || currentSubject().hasRole("Associer Profil-Role") || currentSubject().hasRole("Consulter Fonctionnalité-Role")
                        || currentSubject().hasRole("Consulter Profil-Role")) {
                    this.associationFonctionnaliteRole = "true";
                } else {
                    this.associationFonctionnaliteRole = "false";
                }

                if (currentSubject().hasRole("Associer Fonctionnalité-Role") || currentSubject().hasRole("Désactiver Fonctionnalité-Role")
                        || currentSubject().hasRole("Consulter Fonctionnalité-Role")) {
                    this.consulterFrole = "true";
                } else {
                    this.consulterFrole = "false";
                }

                if (currentSubject().hasRole("Associer Profil-Role")
                        || currentSubject().hasRole("Consulter Profil-Role")) {
                    this.consulterProle = "true";
                } else {
                    this.consulterProle = "false";
                }

                if (currentSubject().hasRole("Associer Fonctionnalité-Role")) {
                    this.associerFrole = "true";
                } else {
                    this.associerFrole = "false";
                }

                if (currentSubject().hasRole("Désactiver Fonctionnalité-Role")) {
                    this.desactiverFrole = "true";
                } else {
                    this.desactiverFrole = "false";
                }

                if (currentSubject().hasRole("Associer Profil-Role")) {
                    this.associerPRole = "true";
                } else {
                    this.associerPRole = "false";
                }

                //GESTION DES COMPTES UTILISATEURS
                if (currentSubject().hasRole("Consulter Utilisateur") || currentSubject().hasRole("Ajouter Utilisateur")
                        || currentSubject().hasRole("Modifier Utilisateur") || currentSubject().hasRole("Consulter Module-Utilisateur")
                        || currentSubject().hasRole("Associer Module-Utilisateur") || currentSubject().hasRole("Désactiver Module-Utilisateur")
                        || currentSubject().hasRole("Consulter Profil") || currentSubject().hasRole("AJouter Profil")
                        || currentSubject().hasRole("Modifier Profil") || currentSubject().hasRole("Consulter Profil-Utilisateur")
                        || currentSubject().hasRole("Associer Profil-Utilisateur") || currentSubject().hasRole("Désactiver Profil-Utilisateur")
                        || currentSubject().hasRole("Consulter Compte") || currentSubject().hasRole("Activer Compte") || currentSubject().hasRole("Désactiver Compte")) {
                    this.compteUtilisateurs = "true";
                } else {
                    this.compteUtilisateurs = "false";
                }

                if (currentSubject().hasRole("Consulter Utilisateur") || currentSubject().hasRole("Ajouter Utilisateur")
                        || currentSubject().hasRole("Modifier Utilisateur")) {
                    this.consulterUtilisateur = "true";
                } else {
                    this.consulterUtilisateur = "false";
                }

                if (currentSubject().hasRole("Ajouter Utilisateur")) {
                    this.ajouterUtilisateur = "true";
                } else {
                    this.ajouterUtilisateur = "false";
                }
                if (currentSubject().hasRole("Modifier Utilisateur")) {
                    this.modifierUtilisateur = "true";
                } else {
                    this.modifierUtilisateur = "false";
                }

                if (currentSubject().hasRole("Consulter Module-Utilisateur")
                        || currentSubject().hasRole("Associer Module-Utilisateur")
                        || currentSubject().hasRole("Désactiver Module-Utilisateur")) {
                    this.consulterMUtilisateur = "true";
                } else {
                    this.consulterMUtilisateur = "false";
                }

                if (currentSubject().hasRole("Associer Module-Utilisateur")) {
                    this.associerMUtilisateur = "true";
                } else {
                    this.associerMUtilisateur = "false";
                }

                if (currentSubject().hasRole("Désactiver Module-Utilisateur")) {
                    this.desactiverMUtilisateur = "true";
                } else {
                    this.desactiverMUtilisateur = "false";
                }

                if (currentSubject().hasRole("Consulter Profil") || currentSubject().hasRole("AJouter Profil")
                        || currentSubject().hasRole("Modifier Profil") || currentSubject().hasRole("Consulter Profil-Utilisateur")
                        || currentSubject().hasRole("Associer Profil-Utilisateur") || currentSubject().hasRole("Désactiver Profil-Utilisateur")
                        || currentSubject().hasRole("Consulter Compte") || currentSubject().hasRole("Activer Compte") || currentSubject().hasRole("Désactiver Compte")) {
                    this.associationProfilUtilisateur = "true";
                } else {
                    this.associationProfilUtilisateur = "false";
                }

                if (currentSubject().hasRole("Consulter Profil") || currentSubject().hasRole("AJouter Profil")
                        || currentSubject().hasRole("Modifier Profil")) {
                    this.consulterProfil = "true";
                } else {
                    this.consulterProfil = "false";
                }

                if (currentSubject().hasRole("AJouter Profil")) {
                    this.ajouterProfil = "true";
                } else {
                    this.ajouterProfil = "false";
                }

                if (currentSubject().hasRole("Modifier Profil")) {
                    this.modifierProfil = "true";
                } else {
                    this.modifierProfil = "false";
                }

                if (currentSubject().hasRole("Consulter Profil-Utilisateur")
                        || currentSubject().hasRole("Associer Profil-Utilisateur") || currentSubject().hasRole("Désactiver Profil-Utilisateur")) {
                    this.consulterPUtilisateur = "true";
                } else {
                    this.consulterPUtilisateur = "false";
                }

                if (currentSubject().hasRole("Associer Profil-Utilisateur")) {
                    this.associerPUtilisateur = "true";
                } else {
                    this.associerPUtilisateur = "false";
                }

                if (currentSubject().hasRole("Désactiver Profil-Utilisateur")) {
                    this.desactiverPUtilisateur = "true";
                } else {
                    this.desactiverPUtilisateur = "false";
                }

                if (currentSubject().hasRole("Consulter Compte") || currentSubject().hasRole("Activer Compte")
                        || currentSubject().hasRole("Désactiver Compte")) {
                    this.consulterCompte = "true";
                } else {
                    this.consulterCompte = "false";
                }

                if (currentSubject().hasRole("Activer Compte")) {
                    this.activerCompte = "true";
                } else {
                    this.activerCompte = "false";
                }

                if (currentSubject().hasRole("Désactiver Compte")) {
                    this.desactiverCompte = "true";
                } else {
                    this.desactiverCompte = "false";
                }

                //GESTION DES UTILISATEURS ET EXTRAS
                if (currentSubject().hasRole("Consulter Historique") || currentSubject().hasRole("Consulter Utilisateur Connectés")) {
                    this.utilisateursExtras = "true";
                } else {
                    this.utilisateursExtras = "false";
                }

                if (currentSubject().hasRole("Consulter Historique")) {
                    this.consulterHistorique = "true";
                } else {
                    this.consulterHistorique = "false";
                }

                if (currentSubject().hasRole("Consulter Utilisateur Connectés")) {
                    this.consulterUtilisateurConnectes = "true";
                } else {
                    this.consulterUtilisateurConnectes = "false";
                }

            }
            journalisation.saveLog4j(LoginBean.class.getSimpleName(), Level.INFO, "Connexion");
            username = "";
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : "dashboard.xhtml");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Nom d'utlisateur ou mot de passe incorrect", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        //return "index";
    }

    public Date sessionTime() {
        return SecurityUtils.getSubject().getSession().getLastAccessTime();
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        try {
            tx.begin();
            journalisation.saveLog4j(LoginBean.class.getSimpleName(), Level.INFO, "Déconnexion");
            currentSubject().logout();
            this.pers = new Utilisateur();
            this.perse = new Utilisateur();
            username = "";
            password = "";
            this.journalisation = new MethodeJournalisation();
            Faces.redirect("login.xhtml");
            username = "";
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.DECONNEXION_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.FATAL, null, ex);
            }
        }
    }

    public void modifierPasse() {
        try {
            if (newPass.trim().equals(retapPass.trim())) {
                pers.setPasswd(new Sha256Hash(newPass.trim()).toHex());
                usbl.updateOne(pers);
                journalisation.saveLog4j(LoginBean.class.getSimpleName(), Level.INFO, "Modification du mot de passe de l'Utilisateur" + pers.getLogin());
                question = "";
                reponse = "";

                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Mot de passe corriger", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
                Faces.redirect("login.xhtml");
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Les mots de passe ne concorde pas", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
            }
        } catch (Exception e) {
        }
    }

    public void modifierPasse2() {
        try {
            if (new Sha256Hash(lastPass).toHex().equals(pers.getPasswd())) {
                if (newPass.trim().equals(retapPass.trim())) {
                    if (new Sha256Hash(newPass).toHex().equals(pers.getPasswd())) {
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Tapez un mot de passe différent de l'ancien", "");
                        FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                        newPass = "";
                        lastPass = "";
                        retapPass = "";
                    } else {
                        pers.setPasswd(new Sha256Hash(newPass.trim()).toHex());
                        usbl.updateOne(pers);
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Mot de passe corrigé", "");
                        FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                        Faces.redirect("login.xhtml");
                    }
                } else {
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Les mots de passe ne concorde pas", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    newPass = "";
                    lastPass = "";
                    retapPass = "";
                }

            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "mot de passe incorrect!!!", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                newPass = "";
                lastPass = "";
                retapPass = "";
            }
        } catch (Exception e) {
        }

    }

    public void reinitialiserPasse() throws IOException {
        try {
            Utilisateur pe = this.usbl.getOneBy("login", per);
            if (pe != null) {
                if (pe.getProfilactif() == true) {
                    if (reponse.equals(pe.getReponse())) {
                        pe.setPasswd(new Sha256Hash("admin").toHex());
                        pe.setQuestion(null);
                        pe.setReponse(null);
                        usbl.updateOne(pe);
                        journalisation.saveLog4j(LoginBean.class.getSimpleName(), Level.INFO, "Réinitialisation du mot de passe de l'Utilisateur" + pe.getLogin());

                        question = "";
                        reponse = "";
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Mot de passe réinitialisé", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                        Faces.redirect("login.xhtml");
                    } else {
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "La reponse saisie est incorreste", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                    }
                } else {
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Vous n'etes pas autorisé à continuer", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                }
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "La reponse saisie est incorreste", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
            }

        } catch (Exception e) {
        }
    }

    public void recupererQuestion() {
        try {
            if (!per.equals("")) {
                Utilisateur pe = this.usbl.getOneBy("login", per);
                if (pe != null) {
                    if (!pe.getPasswd().equals(new Sha256Hash("admin").toHex())) {
                        if (pe.getProfilactif() == true) {
                            this.recupQuestion = pe.getQuestion();
                            Faces.redirect("reinitPass.xhtml");
                        } else {
                            per = "";
                            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "Votre compte est inactif,contactez l'administrateur", "");
                            FacesContext.getCurrentInstance().addMessage("", mf);
                        }
                    } else {
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Connectez vous à votre compte pour changer votre mot de passe", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                    }
                } else {
                    per = "";
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Le login saisi est incorrect", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                }
            }
        } catch (Exception e) {
        }
    }

    public MethodeJournalisation getJournalisation() {
        return journalisation;
    }

    public void setJournalisation(MethodeJournalisation journalisation) {
        this.journalisation = journalisation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRetapPass() {
        return retapPass;
    }

    public void setRetapPass(String retapPass) {
        this.retapPass = retapPass;
    }

    public String getLastPass() {
        return lastPass;
    }

    public void setLastPass(String lastPass) {
        this.lastPass = lastPass;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getConsulterProfil() {
        return consulterProfil;
    }

    public void setConsulterProfil(String consulterProfil) {
        this.consulterProfil = consulterProfil;
    }

    public String getConsulterUtilisateur() {
        return consulterUtilisateur;
    }

    public void setConsulterUtilisateur(String consulterUtilisateur) {
        this.consulterUtilisateur = consulterUtilisateur;
    }

    public String getConsulterCompte() {
        return consulterCompte;
    }

    public void setConsulterCompte(String consulterCompte) {
        this.consulterCompte = consulterCompte;
    }

    public String getAjouterProfil() {
        return ajouterProfil;
    }

    public void setAjouterProfil(String ajouterProfil) {
        this.ajouterProfil = ajouterProfil;
    }

    public String getModifierProfil() {
        return modifierProfil;
    }

    public void setModifierProfil(String modifierProfil) {
        this.modifierProfil = modifierProfil;
    }

    public String getAjouterUtilisateur() {
        return ajouterUtilisateur;
    }

    public void setAjouterUtilisateur(String ajouterUtilisateur) {
        this.ajouterUtilisateur = ajouterUtilisateur;
    }

    public String getModifierUtilisateur() {
        return modifierUtilisateur;
    }

    public void setModifierUtilisateur(String modifierUtilisateur) {
        this.modifierUtilisateur = modifierUtilisateur;
    }

    public String getActiverCompte() {
        return activerCompte;
    }

    public void setActiverCompte(String activerCompte) {
        this.activerCompte = activerCompte;
    }

    public String getDesactiverCompte() {
        return desactiverCompte;
    }

    public void setDesactiverCompte(String desactiverCompte) {
        this.desactiverCompte = desactiverCompte;
    }

    public Utilisateur getPers() {
        return pers;
    }

    public void setPers(Utilisateur pers) {
        this.pers = pers;
    }

    public Utilisateur getPerse() {
        return perse;
    }

    public void setPerse(Utilisateur perse) {
        this.perse = perse;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getRecupQuestion() {
        return recupQuestion;
    }

    public void setRecupQuestion(String recupQuestion) {
        this.recupQuestion = recupQuestion;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getCurentUser() {
        return curentUser;
    }

    public void setCurentUser(String curentUser) {
        this.curentUser = curentUser;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getEspace() {
        return espace;
    }

    public void setEspace(String espace) {
        this.espace = espace;
    }

    public Utilisateur getCurentUsers() {
        return curentUsers;
    }

    public List<ProfilUtilisateur> getProfilUtilisateurs() {
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(List<ProfilUtilisateur> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    public RoleServiceBeanLocal getRsl() {
        return rsl;
    }

    public void setRsl(RoleServiceBeanLocal rsl) {
        this.rsl = rsl;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public ProfilUtilisateurServiceBeanLocal getPusbl() {
        return pusbl;
    }

    public void setPusbl(ProfilUtilisateurServiceBeanLocal pusbl) {
        this.pusbl = pusbl;
    }

    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

    public usersBean getBean() {
        return bean;
    }

    public void setBean(usersBean bean) {
        this.bean = bean;
    }

    public String getTableauSecurite() {
        return tableauSecurite;
    }

    public void setTableauSecurite(String tableauSecurite) {
        this.tableauSecurite = tableauSecurite;
    }

    public String getGestionModule() {
        return gestionModule;
    }

    public void setGestionModule(String gestionModule) {
        this.gestionModule = gestionModule;
    }

    public String getConsulterModule() {
        return consulterModule;
    }

    public void setConsulterModule(String consulterModule) {
        this.consulterModule = consulterModule;
    }

    public String getAjouterModule() {
        return ajouterModule;
    }

    public void setAjouterModule(String ajouterModule) {
        this.ajouterModule = ajouterModule;
    }

    public String getModifierModule() {
        return modifierModule;
    }

    public void setModifierModule(String modifierModule) {
        this.modifierModule = modifierModule;
    }

    public String getModuleFonctionnalite() {
        return moduleFonctionnalite;
    }

    public void setModuleFonctionnalite(String moduleFonctionnalite) {
        this.moduleFonctionnalite = moduleFonctionnalite;
    }

    public String getConsulterFonctionnalite() {
        return consulterFonctionnalite;
    }

    public void setConsulterFonctionnalite(String consulterFonctionnalite) {
        this.consulterFonctionnalite = consulterFonctionnalite;
    }

    public String getAjouterFonctionnalite() {
        return ajouterFonctionnalite;
    }

    public void setAjouterFonctionnalite(String ajouterFonctionnalite) {
        this.ajouterFonctionnalite = ajouterFonctionnalite;
    }

    public String getModifierFonctionnalite() {
        return modifierFonctionnalite;
    }

    public void setModifierFonctionnalite(String modifierFonctionnalite) {
        this.modifierFonctionnalite = modifierFonctionnalite;
    }

    public String getConsulterFModule() {
        return consulterFModule;
    }

    public void setConsulterFModule(String consulterFModule) {
        this.consulterFModule = consulterFModule;
    }

    public String getAssocierFModule() {
        return associerFModule;
    }

    public void setAssocierFModule(String associerFModule) {
        this.associerFModule = associerFModule;
    }

    public String getDesactiverFModule() {
        return desactiverFModule;
    }

    public void setDesactiverFModule(String desactiverFModule) {
        this.desactiverFModule = desactiverFModule;
    }

    public String getAssociationFonctionnaliteRole() {
        return associationFonctionnaliteRole;
    }

    public void setAssociationFonctionnaliteRole(String associationFonctionnaliteRole) {
        this.associationFonctionnaliteRole = associationFonctionnaliteRole;
    }

    public String getConsulterFrole() {
        return consulterFrole;
    }

    public void setConsulterFrole(String consulterFrole) {
        this.consulterFrole = consulterFrole;
    }

    public String getAssocierFrole() {
        return associerFrole;
    }

    public void setAssocierFrole(String associerFrole) {
        this.associerFrole = associerFrole;
    }

    public String getDesactiverFrole() {
        return desactiverFrole;
    }

    public void setDesactiverFrole(String desactiverFrole) {
        this.desactiverFrole = desactiverFrole;
    }

    public String getConsulterProle() {
        return consulterProle;
    }

    public void setConsulterProle(String consulterProle) {
        this.consulterProle = consulterProle;
    }

    public String getAssocierPRole() {
        return associerPRole;
    }

    public void setAssocierPRole(String associerPRole) {
        this.associerPRole = associerPRole;
    }

    public String getCompteUtilisateurs() {
        return compteUtilisateurs;
    }

    public void setCompteUtilisateurs(String compteUtilisateurs) {
        this.compteUtilisateurs = compteUtilisateurs;
    }

    public String getConsulterMUtilisateur() {
        return consulterMUtilisateur;
    }

    public void setConsulterMUtilisateur(String consulterMUtilisateur) {
        this.consulterMUtilisateur = consulterMUtilisateur;
    }

    public String getAssocierMUtilisateur() {
        return associerMUtilisateur;
    }

    public void setAssocierMUtilisateur(String associerMUtilisateur) {
        this.associerMUtilisateur = associerMUtilisateur;
    }

    public String getDesactiverMUtilisateur() {
        return desactiverMUtilisateur;
    }

    public void setDesactiverMUtilisateur(String desactiverMUtilisateur) {
        this.desactiverMUtilisateur = desactiverMUtilisateur;
    }

    public String getAssociationProfilUtilisateur() {
        return associationProfilUtilisateur;
    }

    public void setAssociationProfilUtilisateur(String associationProfilUtilisateur) {
        this.associationProfilUtilisateur = associationProfilUtilisateur;
    }

    public String getConsulterPUtilisateur() {
        return consulterPUtilisateur;
    }

    public void setConsulterPUtilisateur(String consulterPUtilisateur) {
        this.consulterPUtilisateur = consulterPUtilisateur;
    }

    public String getAssocierPUtilisateur() {
        return associerPUtilisateur;
    }

    public void setAssocierPUtilisateur(String associerPUtilisateur) {
        this.associerPUtilisateur = associerPUtilisateur;
    }

    public String getDesactiverPUtilisateur() {
        return desactiverPUtilisateur;
    }

    public void setDesactiverPUtilisateur(String desactiverPUtilisateur) {
        this.desactiverPUtilisateur = desactiverPUtilisateur;
    }

    public String getUtilisateursExtras() {
        return utilisateursExtras;
    }

    public void setUtilisateursExtras(String utilisateursExtras) {
        this.utilisateursExtras = utilisateursExtras;
    }

    public String getConsulterHistorique() {
        return consulterHistorique;
    }

    public void setConsulterHistorique(String consulterHistorique) {
        this.consulterHistorique = consulterHistorique;
    }

    public String getConsulterUtilisateurConnectes() {
        return consulterUtilisateurConnectes;
    }

    public void setConsulterUtilisateurConnectes(String consulterUtilisateurConnectes) {
        this.consulterUtilisateurConnectes = consulterUtilisateurConnectes;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        LoginBean.ip = ip;
    }

    public static Subject getSubject() {
        return subject;
    }

    public static void setSubject(Subject subject) {
        LoginBean.subject = subject;
    }

}
