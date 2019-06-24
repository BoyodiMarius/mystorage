/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.RoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Role;
import com.memoire.mystorage.services.RoleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.Stateless;
import javax.ejb.EJB;

/**
 *
 * @author Armel
 */
@Stateless
public class RoleServiceBean extends mystorageServiceBean<Role, Integer> implements RoleServiceBeanLocal {
 @EJB
 private RoleDaoBeanLocal rdbl;
 
 @Override
 protected mystorageDaoBeanLocal<Role, Integer> getDao(){
     return rdbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
