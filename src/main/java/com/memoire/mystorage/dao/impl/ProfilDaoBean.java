/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.ProfilDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Profil;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class ProfilDaoBean extends mystorageDaoBean<Profil, Integer> implements ProfilDaoBeanLocal {

    public ProfilDaoBean(){
        
    }
    
    @Override
    public Class<Profil> getType(){
        return Profil.class;
    }
            
}

