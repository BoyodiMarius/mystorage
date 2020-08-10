/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.ProfilRoleId;
import com.memoire.mystorage.entities.Role;
import com.memoire.mystorage.services.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.Stateless;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Armel
 */
@Stateless
public class ProfilRoleServiceBean extends mystorageServiceBean<ProfilRole, ProfilRoleId> implements ProfilRoleServiceBeanLocal {

    @EJB
    private ProfilRoleDaoBeanLocal prdbl;

    @Override
    public mystorageDaoBeanLocal<ProfilRole, ProfilRoleId> getDao() {
        return prdbl;
    }


    @Override
    public List<Role> getProfilRoles(Profil profil) {
        return this.prdbl.getProfilRoles(profil);
    }

 
    @Override
    public ProfilRole getProfilRoles(Profil profil, Role role) {
        return this.prdbl.getProfilRoles(profil, role);
    }
    
     @Override
    public boolean supProfilRoles(ProfilRole cRole) {
        return this.prdbl.supProfilRoles(cRole);
    }

}
