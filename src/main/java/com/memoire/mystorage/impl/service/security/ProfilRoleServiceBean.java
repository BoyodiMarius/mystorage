/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.api.service.security.ProfilRoleServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilRoleServiceBean extends mystorageServiceBean<ProfilRole, Long> implements ProfilRoleServiceBeanLocal {

    @EJB
    private ProfilRoleDaoBeanLocal prdbl;

    @Override
    protected mystorageDaoBeanLocal<ProfilRole, Long> getDao() {
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

    @Override
    public List<Role> getFonctionnaliteRoles() {
        return prdbl.getFonctionnaliteRoles();
    }

    @Override
    public List<RoleFonctionnalite> getFonctionnalites(Profil profil) {
        return prdbl.getFonctionnalites(profil);
    }

    @Override
    public List<RoleFonctionnalite> getFonctionnalitesModules(Module modules) {
        return prdbl.getFonctionnalitesModules(modules);
    }

    @Override
    public List<ProfilRole> getProfilRoles1(Profil profil, Role role) {
        return prdbl.getProfilRoles1(profil, role);
    }
}
