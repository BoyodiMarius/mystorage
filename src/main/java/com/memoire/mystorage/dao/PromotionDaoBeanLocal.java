/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Promotion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface PromotionDaoBeanLocal extends mystorageDaoBeanLocal<Promotion, Integer>{

    
     public List<Promotion> getPromotio(Annee annee);
     public List<Promotion> getPromos(Annee annee);
     public List<Promotion> getVague(Integer idAnnee);
         
}
