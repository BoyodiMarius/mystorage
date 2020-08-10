/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.ModuleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.service.security.ModuleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ModuleServiceBean extends mystorageServiceBean<Module, Long> implements ModuleServiceBeanLocal {

    @EJB
    private ModuleDaoBeanLocal mdbl;

    @Override
    public mystorageDaoBeanLocal<Module, Long> getDao() {
        return mdbl;
    }
}
