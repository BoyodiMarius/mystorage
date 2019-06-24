/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Profil;
import java.io.Serializable;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Local
public interface ProfilDaoBeanLocal extends mystorageDaoBeanLocal<Profil, Integer>{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
