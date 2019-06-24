/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;

import com.memoire.mystorage.dao.core.*;
import com.memoire.mystorage.dao.PersonnelDaoBeanLocal;
import com.memoire.mystorage.entities.Personnel;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class PersonnelDaoBean extends mystorageDaoBean<Personnel, Long> implements PersonnelDaoBeanLocal {

    public PersonnelDaoBean() {
    }
    
     @Override
     public Class<Personnel> getType(){
        return Personnel.class;
        
    }
    
}
