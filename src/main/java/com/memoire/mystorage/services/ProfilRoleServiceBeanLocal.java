/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services;


import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.ProfilRoleId;
import com.memoire.mystorage.entities.Role;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface ProfilRoleServiceBeanLocal extends mystorageServiceBeanLocal<ProfilRole, ProfilRoleId>{

    public List<Role> getProfilRoles(Profil profil);

    public ProfilRole getProfilRoles(Profil profil, Role role);    
      public boolean supProfilRoles(ProfilRole cRole);
}
