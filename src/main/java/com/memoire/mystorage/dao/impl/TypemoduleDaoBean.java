/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.TypemoduleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Typemodule;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class TypemoduleDaoBean extends mystorageDaoBean<Typemodule, Integer> implements TypemoduleDaoBeanLocal {

    public TypemoduleDaoBean(){
        
    }
    
    @Override
    public Class<Typemodule> getType(){
        return Typemodule.class;
        
    }
    }
