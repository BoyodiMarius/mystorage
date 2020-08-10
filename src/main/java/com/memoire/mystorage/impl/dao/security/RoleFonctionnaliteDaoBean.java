/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.RoleFonctionnaliteDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class RoleFonctionnaliteDaoBean extends mystorageDaoBean<RoleFonctionnalite, Long> implements RoleFonctionnaliteDaoBeanLocal {

    public RoleFonctionnaliteDaoBean() {
    }

    @Override
    public Class<RoleFonctionnalite> getType() {
        return RoleFonctionnalite.class;
    }

}
