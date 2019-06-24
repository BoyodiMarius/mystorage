/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;

import com.memoire.mystorage.dao.InscriptionDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.InscriptionServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class InscriptionServiceBean extends mystorageServiceBean<Inscription, Integer> implements InscriptionServiceBeanLocal {
 @EJB
 private InscriptionDaoBeanLocal idbl;
 
 @Override
 protected mystorageDaoBeanLocal<Inscription, Integer> getDao(){
     return idbl;
 }
 @Override
    public List<Inscription> getIns(Annee annee) {
        try {
            return idbl.getIns(annee);
        } catch (Exception e) {
            return null;
        }
    
    }
 @Override
    public List<Inscription> getEta(Promotion promotion) {
        try {
            return idbl.getEta(promotion);
        } catch (Exception e) {
            return null;
        }
    
    }
}
