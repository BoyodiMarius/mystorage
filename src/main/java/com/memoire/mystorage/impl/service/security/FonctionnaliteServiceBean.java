/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;


import com.memoire.mystorage.api.dao.security.FonctionnaliteDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.service.security.FonctionnaliteServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class FonctionnaliteServiceBean extends mystorageServiceBean<Fonctionnalite, Long> implements FonctionnaliteServiceBeanLocal {
    
    @EJB
    private FonctionnaliteDaoBeanLocal fdbl;
    
    @Override
    public mystorageDaoBeanLocal<Fonctionnalite, Long> getDao() {
        return fdbl;
    }
    
    @Override
    public List<Fonctionnalite> getFonctionnalitesModules() {
        return fdbl.getFonctionnalitesModules();
    }
    
    @Override
    public List<Fonctionnalite> getFonctionnalitesNonModules() {
        return fdbl.getFonctionnalitesNonModules();
    }
    
    @Override
    public List<Fonctionnalite> getFonctionnalitesModules(Module modules) {
        return fdbl.getFonctionnalitesModules(modules);
    }
    
    @Override
    public List<Fonctionnalite> getModulesFonctionnalites(Module modules) {
        return fdbl.getModulesFonctionnalites(modules);
    }
}
