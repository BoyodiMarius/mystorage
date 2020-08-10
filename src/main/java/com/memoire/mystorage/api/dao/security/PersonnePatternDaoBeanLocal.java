/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.dao.security;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.PersonnePattern;
import javax.ejb.Local;

/**
 *
 * @author kpa001
 */
@Local

public interface PersonnePatternDaoBeanLocal extends mystorageDaoBeanLocal<PersonnePattern, Long>{
    
}
