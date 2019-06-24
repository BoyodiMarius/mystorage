/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;

import com.memoire.mystorage.dao.ParticulierDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Particulier;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class ParticulierDaoBean extends mystorageDaoBean<Particulier, Long> implements ParticulierDaoBeanLocal{

    public ParticulierDaoBean() {
    }
    
    @Override
     public Class<Particulier> getType(){
        return Particulier.class;
        
    }
    
    
}
