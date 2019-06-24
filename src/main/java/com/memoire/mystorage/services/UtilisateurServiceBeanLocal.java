/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;

import com.memoire.mystorage.entities.Utilisateur;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface UtilisateurServiceBeanLocal extends mystorageServiceBeanLocal<Utilisateur, Long> {
    
        public List<Utilisateur> getPersonnesProfil();

    public List<Utilisateur> getPersonnes();

    public List<Utilisateur> getPersonnesNonProfil();
}
