/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.PersonnelDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Personnel;
import com.memoire.mystorage.services.PersonnelServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class PersonnelServiceBean extends mystorageServiceBean<Personnel, Long> implements PersonnelServiceBeanLocal {
 @EJB
 private PersonnelDaoBeanLocal pdbl;
 
 @Override
 protected mystorageDaoBeanLocal<Personnel, Long> getDao(){
     return pdbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
