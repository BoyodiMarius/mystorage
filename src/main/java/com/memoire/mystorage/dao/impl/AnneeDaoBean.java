/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.AnneeDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Annee;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class AnneeDaoBean extends mystorageDaoBean<Annee, Integer> implements AnneeDaoBeanLocal {
    
    public AnneeDaoBean(){
        
    }

 @Override
 public Class<Annee> getType(){
     return Annee.class;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
