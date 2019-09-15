/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;

import com.memoire.mystorage.dao.PromotionDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.PromotionServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class PromotionServiceBean extends mystorageServiceBean<Promotion, Integer> implements PromotionServiceBeanLocal {
 @EJB
 private PromotionDaoBeanLocal prdbl;
 
 @Override
 protected mystorageDaoBeanLocal<Promotion, Integer> getDao(){
     return prdbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<Promotion> getPromotio(Annee annee) {
        try {
            return prdbl.getPromotio(annee);
        } catch (Exception e) {
            return null;
        }
    
    }
    
     @Override
    public List<Promotion> getPromos(Annee annee) {
        try {
            return prdbl.getPromos(annee);
        } catch (Exception e) {
            return null;
        }
    
    }
    
     @Override
    public List<Promotion> getVague(Integer idAnnee) {
        try {
            return this.prdbl.getVague(idAnnee);
        } catch (Exception e) {
            return  Collections.EMPTY_LIST;
        }
    }
}