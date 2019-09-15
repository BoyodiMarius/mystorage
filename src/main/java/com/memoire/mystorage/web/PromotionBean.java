/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.AnneeServiceBeanLocal;
import com.memoire.mystorage.services.ParticulierServiceBeanLocal;
import com.memoire.mystorage.services.PromotionServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.UserTransaction;

/**
 *
 * @author Armel
 */
@Named(value = "PromotionBean")
@ViewScoped
public class PromotionBean implements Serializable {

    @EJB
    private PromotionServiceBeanLocal psbl  ;
    @EJB
    private AnneeServiceBeanLocal asbl;
    @EJB
    private ParticulierServiceBeanLocal psbl1;
    
    private Promotion promotion;
    private Particulier particulier;
    private Annee annee;
    private List<Promotion> promotions;
    private List<Annee> annees;
    private List<Promotion> promo;
    private List<Promotion> promot;
    private List<Particulier> particuliers;

    public PromotionBean() {
        this.promotion = new Promotion();
        this.annee = new Annee();
        this.annees = new ArrayList<>();
        this.promo = new ArrayList<>();
        this.promot = new ArrayList<>();
        this.promotions = new ArrayList<>();
        this.particulier = new Particulier();
    }

    public void save(ActionEvent actionEvent) {
         UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.promotion.setAnnee(annee);
            if (this.promotion.getId()== null) {
                this.promotion.setEtat(true);
                this.psbl.saveOne(promotion);
                 context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
            } else {
                this.psbl.updateOne(promotion);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }
            
        } catch (Exception e) {
            e.getMessage();
             context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
        this.promotion = new Promotion();
    }

    public void cancel(ActionEvent actionEvent) {
        this.promotion = new Promotion();
    }

    public void getObject(Integer id) {
        this.promotion = this.psbl.find(id);

    }

    public void Reouvrir(Integer id) {
          UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        this.promotion = this.psbl.find(id);
        System.out.println(this.promotion); 
        System.out.println(this.promotion.isEtat());
        this.promotion.setEtat(true);
        this.psbl.updateOne(promotion);
          context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
    }
    
    public void Cloturer(Integer id) {
          UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        this.promotion = this.psbl.find(id);
        System.out.println(this.promotion);
        this.promotion.setEtat(false);
        System.out.println(this.promotion.isEtat());
        this.psbl.updateOne(promotion);
          context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
    }

    public PromotionServiceBeanLocal getProsbl() {
        return psbl;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public List<Promotion> getPromotions() {
        this.promotions = this.psbl.getBy("etat", this.promotion.isEtat()== true);
//        for (Promotion p : promo) {
//            if (p.isEtat() == true) {
//                if (!this.promotions.contains(p)) {
//                    this.promotions.add(p);
//                }
//
//            }
//        }
        return promotions;
    }

    public void setProsbl(PromotionServiceBeanLocal prosbl) {
        this.psbl = prosbl;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromo() {
        return promo;
    }

    public void setPromo(List<Promotion> promo) {
        this.promo = promo;
    }

    public List<Promotion> getPromot() {
       this.promot = this.psbl.getBy("etat", this.promotion.isEtat()== false);
//        for (Promotion p : promot) {
//            if (p.isEtat() == false) {
//                if (!this.promot.contains(p)) {
//                    this.promot.add(p);
//                }
//
//            }
//        }
        return promot;
    }

    public void setPromot(List<Promotion> promot) {
        this.promot = promot;
    }

    public AnneeServiceBeanLocal getAsbl() {
        return asbl;
    }

    public void setAsbl(AnneeServiceBeanLocal asbl) {
        this.asbl = asbl;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        this.annees = this.asbl.getAll();
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public PromotionServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(PromotionServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public ParticulierServiceBeanLocal getPsbl1() {
        return psbl1;
    }

    public void setPsbl1(ParticulierServiceBeanLocal psbl1) {
        this.psbl1 = psbl1;
    }

    public Particulier getParticulier() {
        return particulier;
    }

    public void setParticulier(Particulier particulier) {
        this.particulier = particulier;
    }

    public List<Particulier> getParticuliers() {
        return particuliers;
    }

    public void setParticuliers(List<Particulier> particuliers) {
        this.particuliers = particuliers;
    }

    

}
