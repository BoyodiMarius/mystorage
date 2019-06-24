/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;


import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface PromotionServiceBeanLocal extends mystorageServiceBeanLocal<Promotion, Integer> {
      public List<Promotion> getPromotio(Annee annee);
       public List<Promotion> getPromos(Annee annee);
      public List<Promotion> getVague(Integer idAnnee);
}
