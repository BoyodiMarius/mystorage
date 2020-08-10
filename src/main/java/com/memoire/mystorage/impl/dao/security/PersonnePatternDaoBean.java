/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;


import com.memoire.mystorage.api.dao.security.PersonnePatternDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.PersonnePattern;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author kpa001
 */
@Stateless
public class PersonnePatternDaoBean extends mystorageDaoBean<PersonnePattern, Long> implements PersonnePatternDaoBeanLocal {

    public PersonnePatternDaoBean() {
    }

    @Override
    public Class<PersonnePattern> getType() {
        return PersonnePattern.class;
    }

}
