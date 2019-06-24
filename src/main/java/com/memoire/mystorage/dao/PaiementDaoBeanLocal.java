/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;

import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Paiement;
import com.memoire.mystorage.entities.Promotion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface PaiementDaoBeanLocal extends mystorageDaoBeanLocal<Paiement, Integer>{

    public List<Paiement> getPaie(Annee annee);

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<Paiement> getPaie(Promotion promotion);
}
