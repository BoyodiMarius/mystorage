/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.dao.security.PersonnePatternDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.PersonnePattern;
import com.memoire.mystorage.api.service.security.PersonnePatternServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kpa001
 */
@Stateless
public class PersonnePatternServiceBean extends mystorageServiceBean<PersonnePattern, Long> implements PersonnePatternServiceBeanLocal {

    @EJB
    private PersonnePatternDaoBeanLocal ppdbl;

    @Override
    public mystorageDaoBeanLocal<PersonnePattern, Long> getDao() {
        return ppdbl;
    }
}
