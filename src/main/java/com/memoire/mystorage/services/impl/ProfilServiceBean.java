/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.ProfilDaoBeanLocal;
import javax.ejb.Stateless;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.services.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;

/**
 *
 * @author Armel
 */
@Stateless
public class ProfilServiceBean extends mystorageServiceBean<Profil, Integer> implements ProfilServiceBeanLocal {

    @EJB
    private ProfilDaoBeanLocal pdbl;
    
    @Override
    protected mystorageDaoBeanLocal<Profil, Integer> getDao(){
        return pdbl;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
