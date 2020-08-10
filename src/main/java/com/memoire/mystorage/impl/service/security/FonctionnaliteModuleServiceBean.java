/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.FonctionnaliteModuleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.FonctionnaliteModule;
import com.memoire.mystorage.api.service.security.FonctionnaliteModuleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class FonctionnaliteModuleServiceBean extends mystorageServiceBean<FonctionnaliteModule, Long> implements FonctionnaliteModuleServiceBeanLocal {

    @EJB
    private FonctionnaliteModuleDaoBeanLocal fmdbl;

    @Override
    public mystorageDaoBeanLocal<FonctionnaliteModule, Long> getDao() {
        return fmdbl;
    }
}
