/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.dao.security;

import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Brendev
 */
@Local
public interface UtilisateurDaoBeanLocal extends mystorageDaoBeanLocal<Utilisateur, Long> {

    public List<Utilisateur> getUtilisateurs(Module modules);
    
}
