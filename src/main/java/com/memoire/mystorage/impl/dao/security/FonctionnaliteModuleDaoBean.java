/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.FonctionnaliteModuleDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.FonctionnaliteModule;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class FonctionnaliteModuleDaoBean extends mystorageDaoBean<FonctionnaliteModule, Long> implements FonctionnaliteModuleDaoBeanLocal {

    public FonctionnaliteModuleDaoBean() {
    }

    @Override
    public Class<FonctionnaliteModule> getType() {
        return FonctionnaliteModule.class;
    }
}
