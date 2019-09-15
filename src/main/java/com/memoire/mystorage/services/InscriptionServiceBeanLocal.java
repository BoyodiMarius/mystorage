/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;


import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface InscriptionServiceBeanLocal extends mystorageServiceBeanLocal<Inscription, Integer> {
     public List<Inscription> getIns(Annee annee);

    public List<Inscription> getEta(Promotion promotion);
}
