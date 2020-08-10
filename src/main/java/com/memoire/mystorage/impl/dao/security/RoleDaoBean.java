/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;


import com.memoire.mystorage.api.dao.security.RoleDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Brendev
 */
@Stateless
public class RoleDaoBean extends mystorageDaoBean<Role, Long> implements RoleDaoBeanLocal {

    public RoleDaoBean() {
    }

    @Override
    public Class<Role> getType() {
        return Role.class;
    }

    @Override
    public List<Role> getRolesFonctionnalites() {
        return getEntityManager()
                .createQuery("SELECT p FROM Role p WHERE p NOT IN (SELECT pi.role FROM RoleFonctionnalite pi WHERE pi.etat=:etat)")
                .setParameter("etat", true)
                .getResultList();
    }

}
