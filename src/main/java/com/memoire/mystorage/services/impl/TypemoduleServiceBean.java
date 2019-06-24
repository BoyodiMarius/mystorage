/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.TypemoduleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Typemodule;
import com.memoire.mystorage.services.TypemoduleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class TypemoduleServiceBean extends mystorageServiceBean<Typemodule, Integer> implements TypemoduleServiceBeanLocal {
 @EJB
 private TypemoduleDaoBeanLocal tdbl;
 
 @Override
 protected mystorageDaoBeanLocal<Typemodule, Integer> getDao(){
     return tdbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}