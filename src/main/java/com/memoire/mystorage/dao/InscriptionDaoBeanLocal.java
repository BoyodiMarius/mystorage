/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Promotion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface InscriptionDaoBeanLocal extends mystorageDaoBeanLocal<Inscription, Integer>{

  public List<Inscription> getIns(Annee annee);

    public List<Inscription> getEta(Promotion promotion);

    public List<Inscription> getPay(Annee annee);
}