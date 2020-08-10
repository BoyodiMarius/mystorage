/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.UtilisateurDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Brendev
 */
@Stateless
public class UtilisateurDaoBean extends mystorageDaoBean<Utilisateur, Long> implements UtilisateurDaoBeanLocal {

    public UtilisateurDaoBean() {
    }

    @Override
    public Class<Utilisateur> getType() {
        return Utilisateur.class;
    }

    @Override
    public List<Utilisateur> getUtilisateurs(Module modules) {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p NOT IN (SELECT pu.utilisateur FROM UtilisateurModule pu WHERE pu.utilisateur = p AND pu.module =:module AND pu.etat=:etat)")
                .setParameter("module", modules)
                .setParameter("etat", true)
                .getResultList();
    }

}
