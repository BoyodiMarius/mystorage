/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;

import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.entities.Utilisateur;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ParticulierDaoBeanLocal extends mystorageDaoBeanLocal<Particulier, Long>{
    
}
