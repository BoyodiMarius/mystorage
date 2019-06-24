/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.ParticulierDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.services.ParticulierServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class ParticulierServiceBean extends mystorageServiceBean<Particulier, Long> implements ParticulierServiceBeanLocal {
 @EJB
 private ParticulierDaoBeanLocal pdbl;
 
 @Override
 protected mystorageDaoBeanLocal<Particulier, Long> getDao(){
     return pdbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
