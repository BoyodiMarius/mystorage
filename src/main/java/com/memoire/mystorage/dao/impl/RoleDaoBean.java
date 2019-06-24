/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.RoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Role;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class RoleDaoBean extends mystorageDaoBean<Role, Integer>implements RoleDaoBeanLocal {
 public RoleDaoBean() {
    }

    @Override
    public Class<Role> getType(){
        return Role.class;
    }
}