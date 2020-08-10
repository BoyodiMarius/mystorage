/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.FonctionnaliteDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class FonctionnaliteDaoBean extends mystorageDaoBean<Fonctionnalite, Long> implements FonctionnaliteDaoBeanLocal {

    public FonctionnaliteDaoBean() {
    }

    @Override
    public Class<Fonctionnalite> getType() {
        return Fonctionnalite.class;
    }

    @Override
    public List<Fonctionnalite> getFonctionnalitesModules() {
        return getEntityManager()
                .createQuery("SELECT p FROM Fonctionnalite p WHERE p NOT IN (SELECT pi.fonctionnalite FROM FonctionnaliteModule pi WHERE pi.etat=:etat)")
                .setParameter("etat", true)
                .getResultList();
    }

    @Override
    public List<Fonctionnalite> getFonctionnalitesNonModules() {
        return getEntityManager()
                .createQuery("SELECT p FROM Fonctionnalite p WHERE p NOT IN (SELECT pi.fonctionnalite FROM FonctionnaliteModule pi WHERE pi.etat=:etat)")
                .setParameter("etat", false)
                .getResultList();
    }

    @Override
    public List<Fonctionnalite> getFonctionnalitesModules(Module modules) {
        return getEntityManager()
                .createQuery("SELECT p FROM Fonctionnalite p WHERE p NOT IN (SELECT pi.fonctionnalite FROM FonctionnaliteModule pi WHERE pi.fonctionnalite=p AND pi.modules=:module AND pi.etat=:etat)")
                .setParameter("module", modules)
                .setParameter("etat", true)
                .getResultList();
    }
    
    @Override
    public List<Fonctionnalite> getModulesFonctionnalites(Module modules) {
        return getEntityManager()
                .createQuery("SELECT p.fonctionnalite FROM FonctionnaliteModule p WHERE p.modules=:modules AND p.etat=:etat")
                .setParameter("modules", modules)
                .setParameter("etat", true)
                .getResultList();
    }
    

}
