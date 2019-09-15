/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;


import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Paiement;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface PaiementServiceBeanLocal extends mystorageServiceBeanLocal<Paiement, Integer> {

    public List<Paiement> getPaie(Promotion promotion);

    public List<Paiement> getPaie(Annee annee);
    
}
