/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.AnneeDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.services.AnneeServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class AnneeServiceBean extends mystorageServiceBean<Annee, Integer> implements AnneeServiceBeanLocal {
@EJB
private AnneeDaoBeanLocal adbl;

@Override
protected mystorageDaoBeanLocal<Annee, Integer> getDao(){
    return adbl;
}
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    
}
