/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.service.security;


import com.memoire.mystorage.api.entities.security.Fonctionnalite;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface FonctionnaliteServiceBeanLocal extends mystorageServiceBeanLocal<Fonctionnalite, Long> {

    public List<Fonctionnalite> getFonctionnalitesModules();

    public List<Fonctionnalite> getFonctionnalitesNonModules();

    public List<Fonctionnalite> getFonctionnalitesModules(Module modules);

    public List<Fonctionnalite> getModulesFonctionnalites(Module modules);
    
}
