/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.service.security;

import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface ProfilRoleServiceBeanLocal extends mystorageServiceBeanLocal<ProfilRole, Long> {

    public List<Role> getProfilRoles(Profil profil);

    public ProfilRole getProfilRoles(Profil profil, Role role);

    public boolean supProfilRoles(ProfilRole cRole);

    public List<Role> getFonctionnaliteRoles();

    public List<RoleFonctionnalite> getFonctionnalites(Profil profil);

    public List<RoleFonctionnalite> getFonctionnalitesModules(Module modules);

    public List<ProfilRole> getProfilRoles1(Profil profil, Role role);
}
