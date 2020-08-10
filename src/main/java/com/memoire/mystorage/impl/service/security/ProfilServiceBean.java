/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.ProfilDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilServiceBean extends mystorageServiceBean<Profil, Long> implements ProfilServiceBeanLocal {

    @EJB
    private ProfilDaoBeanLocal pdbl;

    @Override
    protected mystorageDaoBeanLocal<Profil, Long> getDao() {
        return pdbl;
    }
}
