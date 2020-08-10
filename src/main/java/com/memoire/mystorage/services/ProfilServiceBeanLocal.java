/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;


import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.io.Serializable;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface ProfilServiceBeanLocal extends mystorageServiceBeanLocal<Profil, Integer> {
    
}
