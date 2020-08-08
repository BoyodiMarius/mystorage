/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;

import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.Role;
import com.memoire.mystorage.entities.Utilisateur;
import com.memoire.mystorage.services.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.services.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.RoleServiceBeanLocal;
import com.memoire.mystorage.services.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.shiro.EntityRealm;
import com.memoire.mystorage.transaction.TransactionManager;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Brendev
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Utilisateur pers;
    private Utilisateur perse;
    private static Utilisateur curentUsers;
    private List<Profil> profils, profilUtilisateurs;
    private List<Utilisateur> us;
    @EJB
    private RoleServiceBeanLocal rsbl;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;


    private Date date = new Date();
    private String curentUser;
    private String tribunal;
    private String username;
    private String password;
    private String newPass;
    private String retapPass;
    private String lastPass;
    private String email;
    String pass = "";
    private String per = "";
    private String question;
    private String reponse, recupQuestion;

    //-I- TABLEAU DE BORD
    //-V-UTILISATEURS ET EXTRAS
    private String utilisateursExtras;
    private String consulterHistorique, consulterUtilisateurConnectes;
    private String creerInscription, modifierInscription, vague, creerPromotion, modifierPromotion, creerTypemodule, modifierTypemodule, creerPaiement, modifierPaiement, creerutilisateur, modifierutilisateur,
            utilisateur, creerannee, modifierannee, creeraffectation, modifieraffectation, stats,
            affecterMembre, validerMembre, creerPersonnel, modifierPersonnel, profil, creerProfil, modifierProfil, associerProfil, associerRole,
            activerCompte, desactiverCompte, administration, rapport, etat, securite, personnel, inscription,
            promotion, Typemodule, paiement, affectation, evaluer, annee;

    private static String ip;
    private static Subject subject;
    private boolean remember = true;
    private boolean admin;
    private String space = " | ";
    private String espace = " ";

    public LoginBean() {
        pers = new Utilisateur();
        perse = new Utilisateur();
        this.profilUtilisateurs = new ArrayList<>();

    }

    @PostConstruct
    public void init() {
        System.out.println("login");
        boolean test = this.checkIntConnection();
        FacesContext context = FacesContext.getCurrentInstance();
        UserTransaction tx = TransactionManager.getUserTransaction();
        if (test) {
            context.addMessage(null, new FacesMessage("Connexion internet disponible"));
        } else {
            context.addMessage(null, new FacesMessage("Connexion internet non disponible"));
        }

        List<Role> all = rsbl.getAll();
        if (all.isEmpty()) {
            
            this.rsbl.saveOne(new Role("Créer utilisateur"));
            this.rsbl.saveOne(new Role("Modifier utilisateur"));         
            this.rsbl.saveOne(new Role("Créer profil"));
            this.rsbl.saveOne(new Role("Modifier profil"));
            this.rsbl.saveOne(new Role("Creer securite"));
            this.rsbl.saveOne(new Role("Associer profil"));
            this.rsbl.saveOne(new Role("Associer role"));
            this.rsbl.saveOne(new Role("Activer compte"));
            this.rsbl.saveOne(new Role("Désactiver compte"));
            this.rsbl.saveOne(new Role("Modifier securite"));
        }
        List<Profil> alle = psbl.getAll();
        if (alle.isEmpty()) {
            this.psbl.saveOne(new Profil("administrateur"));
            //this.psbl.saveOne(new Profil("Gerer"));
            //this.psbl.saveOne(new Profil("auditer"));
            this.psbl.saveOne(new Profil("utilisateur"));
        }

        List<Profil> profils = psbl.getBy("nom", "administrateur");
        System.out.println("profil "+profils.size());
        if (!profils.isEmpty()) {
            try {
                tx.begin();
                List<Role> roles = this.rsbl.getAll();
                for (Role role : roles) {
                    ProfilRole pr = new ProfilRole();
                    pr.setRole(role);
                    pr.setProfil(psbl.getOneBy("nom", "administrateur"));
                    prsbl.saveOne(pr);
                }

                Utilisateur p = new Utilisateur();
                System.out.println("creation de compte");
                p.setLogin("Admin");
                p.setPass(new Sha256Hash("@frica").toHex());
                p.setProfil(psbl.getOneBy("nom", "administrateur"));
                p.setActif(true);

                usbl.saveOne(p);
                tx.commit();
                System.out.println("Compte creer");
            } catch (Exception e) {
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.ERROR, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.ERROR, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(UtilisateurBean.class.getName()).log(Level.ERROR, null, ex);
                } catch (AuthenticationException ex) {
                    ex.printStackTrace();

                }
            }
        }
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

    public boolean checkIntConnection() {
        boolean status = false;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        try {
            sock.connect(address, 3000);
            if (sock.isConnected()) {
                status = true;
            }
        } catch (Exception e) {

        } finally {
            try {
                sock.close();
            } catch (Exception e) {

            }
        }

        return status;
    }

    public String identite() {
        String iden = " Admin";
        try {
            if (LoginBean.currentPersonnel() != null) {
                iden = LoginBean.currentPersonnel().getNom().concat(" ").concat(LoginBean.currentPersonnel().getPrenom());
            }
        } catch (Exception e) {
        }
        return iden;
    }

    public String profilUtilisateur() {
        String profile = " ";
        try {
            if (LoginBean.currentPersonnel() != null) {
                profile = LoginBean.currentPersonnel().getProfil().getLibelle();
            }
        } catch (Exception e) {
        }
        return profile;
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
                if (pers.isActif() == false) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('test').show();");
                    username = "";
                    return;
                }
            }

            if (pers != null) {
                boolean verifPass = new Sha256Hash(password).toHex().equals(pers.getPass());
                System.out.println("pass "+verifPass);
                if (verifPass && pers.isActif() == true && pers.getProfil().getNom().equalsIgnoreCase("utilisateur")) {
              
                    RequestContext context = RequestContext.getCurrentInstance();
                    SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
                    Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : "file_1.xhtml");
                    //context.execute("PF('typeAjout').show();");
                    //return;
                } else if (verifPass && pers.isActif() == true && pers.getProfil().getNom().equalsIgnoreCase("administrateur")) {
                    SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
                    Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : "stat.xhtml");
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Nom d'utlisateur ou mot de passe incorrect"));
                    
                }
                
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Nom d'utlisateur ou mot de passe incorrect"));
            }
         
            UsernamePasswordToken token = new UsernamePasswordToken(username.trim(), password.trim());
            //”Remember Me” built-in, just do this:
            token.setRememberMe(false);

            SecurityUtils.getSubject().login(token);

            Subject subjects = EntityRealm.getSubject();

            if (!username.equalsIgnoreCase("admin")) {

                if (subjects.hasRole("Créer profil") || subjects.hasRole("Modifier profil")
                        || subjects.hasRole("Associer profil")
                        || subjects.hasRole("Associer role") || subjects.hasRole("Activer compte")
                        || subjects.hasRole("Désactiver compte")) {
                    this.securite = "true";
                } else {
                    this.securite = "false";
                }

                if (subjects.hasRole("Créer utilisateur") || subjects.hasRole("Modifier utilisateur")) {
                    this.utilisateur = "true";
                } else {
                    this.utilisateur = "false";
                }

                if (subjects.hasRole("Créer utilisateur")) {
                    this.creerutilisateur = "true";
                } else {
                    this.creerutilisateur = "false";
                }

                if (subjects.hasRole("Créer profil") || subjects.hasRole("Modifier profil")) {
                    this.profil = "true";
                } else {
                    this.profil = "false";
                }

                if (subjects.hasRole("Créer profil")) {
                    this.creerProfil = "true";
                } else {
                    this.creerProfil = "false";
                }

                if (subjects.hasRole("Associer profil")) {
                    this.associerProfil = "true";
                } else {
                    this.associerProfil = "false";
                }

                if (subjects.hasRole("Associer role")) {
                    this.associerRole = "true";
                } else {
                    this.associerRole = "false";
                }

                if (subjects.hasRole("Activer compte")) {
                    this.activerCompte = "true";
                } else {
                    this.activerCompte = "false";
                }

                if (subjects.hasRole("Désactiver compte")) {
                    this.desactiverCompte = "true";
                } else {
                    this.desactiverCompte = "false";
                }
                if (subjects.hasRole("voir statistiques")) {
                    this.stats = "true";
                } else {
                    this.stats = "false";
                }

            }

            

        } catch (AuthenticationException e) {
            e.getMessage();
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
        try {
            EntityRealm.getSubject().logout();
            Faces.redirect("Bienvenue.xhtml");
            username = "";
        } catch (IOException ex) {
        }

    }

    public void modifierPasse() {
        try {
            if (new Sha256Hash(password.trim()).toHex().equals(pers.getPass())) {
                if (!newPass.equals(password)) {
                    if (newPass.trim().equals(retapPass.trim())) {
                        pers.setPass(new Sha256Hash(newPass.trim()).toHex());
                        pers.setFirstconnect(false);
                        usbl.updateOne(pers);
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Mot de passe corriger", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);

                        pers = new Utilisateur();
                        password = " ";
                        Faces.redirect("login.xhtml");
                    } else {
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Les mots de passe ne concorde pas", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                    }
                } else {
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Vous ne pouvez pas choisir le même mot de passe", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                }
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Le mot de passe n'est pas correct", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
            }
        } catch (Exception e) {
        }
    }

    public void modifierPasse2() {
        try {
            if (new Sha256Hash(lastPass.trim()).toHex().equals(pers.getPass())) {
                if (!newPass.equals(lastPass)) {
                    if (newPass.trim().equals(retapPass.trim())) {
                        pers.setPass(new Sha256Hash(newPass.trim()).toHex());
                        pers.setFirstconnect(false);
                        usbl.updateOne(pers);
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Mot de passe corriger", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                        pers = new Utilisateur();
                    } else {
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Les mots de passe ne concorde pas", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                    }
                } else {
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Vous ne pouvez pas choisir le même mot de passe", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                }
            } else {
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Le mot de passe n'est pas correct", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
            }
        } catch (Exception e) {
        }
    }

    public String recupererQuestion() {
        if (!per.equals("")) {
            Utilisateur pe = this.usbl.getOneBy("login", per);
            String quest = "";
            if (pe != null) {
                if (!pe.getPass().equals(new Sha256Hash("admin").toHex())) {
                    if (pe.isActif() == true) {
                        quest = pe.getQuestion();
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('dialogRecup').show();");
                        context.execute("PF('dialogOublie').hide();");
                        return quest;
                    } else {
                        per = "";
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("PF('dialogOublie').hide();");
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Votre compte est inactif,contactez l'administrateur", "");
                        FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                    }
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dialogOublie').hide();");
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Connectez vous à votre compte pour changer votre mot de passe", "");
                    FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
                }
            } else {
                per = "";
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "le login saisi est inconnu", "");
                FacesContext.getCurrentInstance().addMessage("erreur_login", mf);
            }
        }
        return "";
    }

    public void reinitialiserPasse() throws IOException {
        try {
            Utilisateur pe = this.usbl.getOneBy("login", per);
            if (pe != null) {
                if (pe.isActif() == true) {
                    pe.setPass(new Sha256Hash("admin").toHex());
                    usbl.updateOne(pe);
                    question = "";
                    reponse = "";
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Mot de passe réinitialisé", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                    Faces.redirect("login.xhtml");

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

        } catch (IOException e) {
        }
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

    public static Utilisateur getCurentUsers() {
        return curentUsers;
    }

    public static void setCurentUsers(Utilisateur curentUsers) {
        LoginBean.curentUsers = curentUsers;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public List<Profil> getProfilUtilisateurs() {
        return profilUtilisateurs;
    }

    public void setProfilUtilisateurs(List<Profil> profilUtilisateurs) {
        this.profilUtilisateurs = profilUtilisateurs;
    }

    public List<Utilisateur> getUs() {
        return us;
    }

    public void setUs(List<Utilisateur> us) {
        this.us = us;
    }

    public RoleServiceBeanLocal getRsbl() {
        return rsbl;
    }

    public void setRsbl(RoleServiceBeanLocal rsbl) {
        this.rsbl = rsbl;
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

    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getRecupQuestion() {
        return recupQuestion;
    }

    public void setRecupQuestion(String recupQuestion) {
        this.recupQuestion = recupQuestion;
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

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getEspace() {
        return espace;
    }

    public void setEspace(String espace) {
        this.espace = espace;
    }

    public String getCreerInscription() {
        return creerInscription;
    }

    public void setCreerInscription(String creerInscription) {
        this.creerInscription = creerInscription;
    }

    public String getModifierInscription() {
        return modifierInscription;
    }

    public void setModifierInscription(String modifierInscription) {
        this.modifierInscription = modifierInscription;
    }

    public String getVague() {
        return vague;
    }

    public void setVague(String vague) {
        this.vague = vague;
    }

    public String getCreerPromotion() {
        return creerPromotion;
    }

    public void setCreerPromotion(String creerPromotion) {
        this.creerPromotion = creerPromotion;
    }

    public String getModifierPromotion() {
        return modifierPromotion;
    }

    public void setModifierPromotion(String modifierPromotion) {
        this.modifierPromotion = modifierPromotion;
    }

    public String getCreerTypemodule() {
        return creerTypemodule;
    }

    public void setCreerTypemodule(String creerTypemodule) {
        this.creerTypemodule = creerTypemodule;
    }

    public String getModifierTypemodule() {
        return modifierTypemodule;
    }

    public void setModifierTypemodule(String modifierTypemodule) {
        this.modifierTypemodule = modifierTypemodule;
    }

    public String getCreerPaiement() {
        return creerPaiement;
    }

    public void setCreerPaiement(String creerPaiement) {
        this.creerPaiement = creerPaiement;
    }

    public String getModifierPaiement() {
        return modifierPaiement;
    }

    public void setModifierPaiement(String modifierPaiement) {
        this.modifierPaiement = modifierPaiement;
    }

    public String getCreerutilisateur() {
        return creerutilisateur;
    }

    public void setCreerutilisateur(String creerutilisateur) {
        this.creerutilisateur = creerutilisateur;
    }

    public String getModifierutilisateur() {
        return modifierutilisateur;
    }

    public void setModifierutilisateur(String modifierutilisateur) {
        this.modifierutilisateur = modifierutilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getCreerannee() {
        return creerannee;
    }

    public void setCreerannee(String creerannee) {
        this.creerannee = creerannee;
    }

    public String getModifierannee() {
        return modifierannee;
    }

    public void setModifierannee(String modifierannee) {
        this.modifierannee = modifierannee;
    }

    public String getCreeraffectation() {
        return creeraffectation;
    }

    public void setCreeraffectation(String creeraffectation) {
        this.creeraffectation = creeraffectation;
    }

    public String getModifieraffectation() {
        return modifieraffectation;
    }

    public void setModifieraffectation(String modifieraffectation) {
        this.modifieraffectation = modifieraffectation;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getAffecterMembre() {
        return affecterMembre;
    }

    public void setAffecterMembre(String affecterMembre) {
        this.affecterMembre = affecterMembre;
    }

    public String getValiderMembre() {
        return validerMembre;
    }

    public void setValiderMembre(String validerMembre) {
        this.validerMembre = validerMembre;
    }

    public String getCreerPersonnel() {
        return creerPersonnel;
    }

    public void setCreerPersonnel(String creerPersonnel) {
        this.creerPersonnel = creerPersonnel;
    }

    public String getModifierPersonnel() {
        return modifierPersonnel;
    }

    public void setModifierPersonnel(String modifierPersonnel) {
        this.modifierPersonnel = modifierPersonnel;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getCreerProfil() {
        return creerProfil;
    }

    public void setCreerProfil(String creerProfil) {
        this.creerProfil = creerProfil;
    }

    public String getModifierProfil() {
        return modifierProfil;
    }

    public void setModifierProfil(String modifierProfil) {
        this.modifierProfil = modifierProfil;
    }

    public String getAssocierProfil() {
        return associerProfil;
    }

    public void setAssocierProfil(String associerProfil) {
        this.associerProfil = associerProfil;
    }

    public String getAssocierRole() {
        return associerRole;
    }

    public void setAssocierRole(String associerRole) {
        this.associerRole = associerRole;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getSecurite() {
        return securite;
    }

    public void setSecurite(String securite) {
        this.securite = securite;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getInscription() {
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getTypemodule() {
        return Typemodule;
    }

    public void setTypemodule(String Typemodule) {
        this.Typemodule = Typemodule;
    }

    public String getPaiement() {
        return paiement;
    }

    public void setPaiement(String paiement) {
        this.paiement = paiement;
    }

    public String getAffectation() {
        return affectation;
    }

    public void setAffectation(String affectation) {
        this.affectation = affectation;
    }

    public String getEvaluer() {
        return evaluer;
    }

    public void setEvaluer(String evaluer) {
        this.evaluer = evaluer;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

}
