/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.dao.security;


import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface ProfilRoleDaoBeanLocal extends mystorageDaoBeanLocal<ProfilRole, Long> {

    public List<Role> getProfilRoles(Profil profil);

    public ProfilRole getProfilRoles(Profil profil, Role role);

    public boolean supProfilRoles(ProfilRole cRole);

    public List<Role> getFonctionnaliteRoles();

    public List<RoleFonctionnalite> getFonctionnalites(Profil profil);

    public List<RoleFonctionnalite> getFonctionnalitesModules(Module modules);

    public List<ProfilRole> getProfilRoles1(Profil profil, Role role);
}
