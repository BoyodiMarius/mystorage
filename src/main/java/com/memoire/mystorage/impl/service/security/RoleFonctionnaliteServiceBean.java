/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.RoleFonctionnaliteDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.api.service.security.RoleFonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class RoleFonctionnaliteServiceBean extends mystorageServiceBean<RoleFonctionnalite, Long> implements RoleFonctionnaliteServiceBeanLocal {

    @EJB
    private RoleFonctionnaliteDaoBeanLocal rfdbl;

    @Override
    public mystorageDaoBeanLocal<RoleFonctionnalite, Long> getDao() {
        return rfdbl;
    }
}
