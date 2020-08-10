/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.RoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.service.security.RoleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Brendev
 */
@Stateless
public class RoleServiceBean extends mystorageServiceBean<Role, Long> implements RoleServiceBeanLocal {

    @EJB
    private RoleDaoBeanLocal rdbl;

    @Override
    public mystorageDaoBeanLocal<Role, Long> getDao() {
        return rdbl;
    }

    @Override
    public List<Role> getRolesFonctionnalites() {
        return rdbl.getRolesFonctionnalites();
    }
}
