/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Paiement;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.services.AnneeServiceBeanLocal;
import com.memoire.mystorage.services.InscriptionServiceBeanLocal;
import com.memoire.mystorage.services.PaiementServiceBeanLocal;
import com.memoire.mystorage.services.ParticulierServiceBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Armel
 */
@Named(value = "AnneeBean")
@ViewScoped
public class AnneeBean implements Serializable {

    /**
     * Creates a new instance of ProfilBean
     */
    private Annee annee;
    private Inscription inscription;
    private Particulier particulier;
    private Paiement paiement;
    private List<Annee> Annees, Anned, Annexes,ann,anno;

    @EJB
    private AnneeServiceBeanLocal asbl;
    @EJB
    private InscriptionServiceBeanLocal isbl;
    @EJB
    private ParticulierServiceBeanLocal psbl;
    @EJB 
    private PaiementServiceBeanLocal psbl1;
   
    public AnneeBean() {
        this.annee = new Annee();
        this.particulier = new Particulier();
        this.inscription = new Inscription();
        this.paiement = new Paiement();
        this.Anned = new ArrayList<>();
        this.Annexes = new ArrayList<>();
        this.Annees = new ArrayList<>();
        this.ann = new ArrayList<>();
        this.anno = new ArrayList<>();
    }

    public void save() {
        try {
            if (this.annee.getId() == null) {
                this.annee.setEtat(true);
                this.asbl.saveOne(annee);
            } else {
                this.asbl.updateOne(annee);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        this.annee = new Annee();
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public AnneeServiceBeanLocal getAsbl() {
        return asbl;
    }

    public void setAsbl(AnneeServiceBeanLocal asbl) {
        this.asbl = asbl;
    }

    public List<Annee> getAnnees() {
        this.Annees = this.asbl.getBy("etat", this.annee.isEtat()== false);
//         for (Annee b : anno) {
//            if (b.isEtat() == false) {
//                if (!this.Annees.contains(b)) {
//                    this.Annees.add(b);
//                }
//
//            }
//        }
        return Annees;
    }

    public void setAnnees(List<Annee> Annees) {
        this.Annees = Annees;
    }

    public List<Annee> getAnned() {
        this.Anned = this.asbl.getBy("etat", this.annee.isEtat() == true);
        return Anned;
    }

    public void setAnned(List<Annee> Anned) {
        this.Anned = Anned;
    }

    public List<Annee> getAnnexes() {
        this.Annexes = this.asbl.getBy("etat", this.annee.isEtat()== true);
//        for (Annee a : ann) {
//            if (a.isEtat() == true) {
//                if (!this.Annexes.contains(a)) {
//                    this.Annees.add(a);
//                }
//
//            }
   //     }
        return Annexes;
    }

       public void Cloturer(Integer id) {
        this.annee = this.asbl.find(id);
        System.out.println(this.annee); 
        System.out.println(this.annee.isEtat());
        this.annee.setEtat(false);
        this.asbl.updateOne(annee);
    }
       
         public void Reouvrir(Integer id) {
        this.annee = this.asbl.find(id);
        System.out.println(this.annee); 
        System.out.println(this.annee.isEtat());
        this.annee.setEtat(true);
        this.asbl.updateOne(annee);
    }
    
    public void setAnnexes(List<Annee> Annexes) {
        this.Annexes = Annexes;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public List<Annee> getAnn() {
        return ann;
    }

    public void setAnn(List<Annee> ann) {
        this.ann = ann;
    }

    public InscriptionServiceBeanLocal getIsbl() {
        return isbl;
    }

    public void setIsbl(InscriptionServiceBeanLocal isbl) {
        this.isbl = isbl;
    }

    public List<Annee> getAnno() {
        return anno;
    }

    public void setAnno(List<Annee> anno) {
        this.anno = anno;
    }
    
    
}
