/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.UtilisateurDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Brendev
 */
@Stateless
public class UtilisateurServiceBean extends mystorageServiceBean<Utilisateur, Long> implements UtilisateurServiceBeanLocal {

    @EJB
    private UtilisateurDaoBeanLocal udbl;

    @Override
    protected mystorageDaoBeanLocal<Utilisateur, Long> getDao() {
        return udbl;
    }

    @Override
    public List<Utilisateur> getUtilisateurs(Module modules) {
        return udbl.getUtilisateurs(modules);
    }

}
