/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;

import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.dao.PersonnelDaoBeanLocal;
import com.memoire.mystorage.dao.PromotionDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Personnel;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.AnneeServiceBeanLocal;
import com.memoire.mystorage.services.PersonnelServiceBeanLocal;
import com.memoire.mystorage.services.PromotionServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 *
 * @author Armel
 */
@Named(value = "PersonnelBean")
@ViewScoped
public class PersonnelBean implements Serializable {

    /**
     * Creates a new instance of UtilisateurBean
     */
    private Personnel personnel;
    private Annee annee;
    private List<Personnel> personnels;
    private Profil Gerer;
    private List<Promotion> prom, proms;
    private List<Annee> annees, annet;
    private Promotion promotion;
    @EJB
    private PromotionServiceBeanLocal prosbl;
    @EJB
    private PersonnelServiceBeanLocal prsbl;
    @EJB
    private ProfilServiceBeanLocal psbl;
    @EJB
    private PromotionDaoBeanLocal pdbl;
    @EJB
    private AnneeServiceBeanLocal asbl;
    @EJB
    private PersonnelDaoBeanLocal pdbl1;

    public PersonnelBean() {
        this.personnel = new Personnel();
        this.personnels = new ArrayList<>();
        this.prom = new ArrayList<>();
        this.Gerer = new Profil();
        this.annees = new ArrayList<>();
        this.annet = new ArrayList<>();
        this.proms = new ArrayList<>();
    }

    public void cancel(ActionEvent actionEvent) {
        this.personnel = new Personnel();
    }

    public void save(ActionEvent actionEvent) {
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            tx.begin();
            if (this.personnel.getId() == null) {
                this.personnel.setLogin(this.personnel.getPersonnePattern().getMail());
                this.personnel.setPasswd(new Sha256Hash("admin").toHex());
                this.personnel.setProfilactif(true);
                Gerer = this.psbl.getOneBy("nom", "Gerer");

                this.prsbl.saveOne(personnel);
                String log = "login" + "" + this.personnel.getPersonnePattern().getMail();
                String pass = "password" + "=" + "admin";
                String test = "CAGECFI ACADEMY vient par ce fait vous soumettre vos informations de connexion. Votre " + " " + log + " " + " et votre " + pass;
                // SendMailByGlassfish.runTest(test, this.personnel.getEmail(), "CAGECFI ACADEMY", "Informations");
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));

            } else {
                this.prsbl.updateOne(personnel);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }
            tx.commit();
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(PersonnelBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(PersonnelBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(PersonnelBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.personnel = new Personnel();
        this.Gerer = new Profil();
        this.promotion = new Promotion();
    }

    public Date max() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, -20);
        return ca.getTime();
    }

    public Date max1() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, 0);
        return ca.getTime();
    }

    public void onAnneChange() {
        if (annee != null && !annee.equals("")) {
            proms.clear();
            proms.addAll(this.prosbl.getVague(annee.getId()));
        }
    }

    public void getObject(Long id) {
        this.personnel = this.prsbl.find(id);
        System.out.println(id);

    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Personnel> getPersonnels() {
        this.personnels = this.prsbl.getAll();
        return personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
        this.personnels = personnels;
    }

    public Profil getGerer() {
        return Gerer;
    }

    public void setGerer(Profil Gerer) {
        this.Gerer = Gerer;
    }

    public List<Promotion> getProm() {
        return prom;
    }

    public void setProm(List<Promotion> prom) {
        this.prom = prom;
    }

    public List<Promotion> getProms() {
        return proms;
    }

    public void setProms(List<Promotion> proms) {
        this.proms = proms;
    }

    public List<Annee> getAnnees() {
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public List<Annee> getAnnet() {
        return annet;
    }

    public void setAnnet(List<Annee> annet) {
        this.annet = annet;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public PromotionServiceBeanLocal getProsbl() {
        return prosbl;
    }

    public void setProsbl(PromotionServiceBeanLocal prosbl) {
        this.prosbl = prosbl;
    }

    public PersonnelServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setPrsbl(PersonnelServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }

    public ProfilServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ProfilServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public PromotionDaoBeanLocal getPdbl() {
        return pdbl;
    }

    public void setPdbl(PromotionDaoBeanLocal pdbl) {
        this.pdbl = pdbl;
    }

    public AnneeServiceBeanLocal getAsbl() {
        return asbl;
    }

    public void setAsbl(AnneeServiceBeanLocal asbl) {
        this.asbl = asbl;
    }

    public PersonnelDaoBeanLocal getPdbl1() {
        return pdbl1;
    }

    public void setPdbl1(PersonnelDaoBeanLocal pdbl1) {
        this.pdbl1 = pdbl1;
    }

}
