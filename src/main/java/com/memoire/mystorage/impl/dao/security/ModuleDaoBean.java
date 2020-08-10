/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.ModuleDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ModuleDaoBean extends mystorageDaoBean<Module, Long> implements ModuleDaoBeanLocal {

    public ModuleDaoBean() {
    }

    @Override
    public Class<Module> getType() {
        return Module.class;
    }
}
