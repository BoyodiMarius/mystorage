/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.ProfilDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilDaoBean extends mystorageDaoBean<Profil, Long> implements ProfilDaoBeanLocal {

    public ProfilDaoBean() {
    }

    @Override
    public Class<Profil> getType() {
        return Profil.class;
    }
}
