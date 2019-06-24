/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.PaiementDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Paiement;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.PaiementServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class PaiementServiceBean extends mystorageServiceBean<Paiement, Integer> implements PaiementServiceBeanLocal {
 @EJB
 private PaiementDaoBeanLocal paidbl;
 
 @Override
 protected mystorageDaoBeanLocal<Paiement, Integer> getDao(){
     return paidbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 
 
 @Override
    public List<Paiement> getPaie(Annee annee) {
        try {
            return paidbl.getPaie(annee);
        } catch (Exception e) {
            return null;
        }
    
    }
    @Override
    public List<Paiement> getPaie(Promotion promotion) {
        try {
            return paidbl.getPaie(promotion);
        } catch (Exception e) {
            return null;
        }
    
    }
}
