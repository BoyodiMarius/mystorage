/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.ProfilRoleId;
import com.memoire.mystorage.entities.Role;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class ProfilRoleDaoBean extends mystorageDaoBean<ProfilRole, ProfilRoleId> implements ProfilRoleDaoBeanLocal {

     public ProfilRoleDaoBean() {
    }

    @Override
    public Class<ProfilRole> getType() {
        return ProfilRole.class;
    }


    @Override
    public List<Role> getProfilRoles(Profil profil) {
        return getEntityManager()
                .createQuery("SELECT p.role FROM ProfilRole p "
                        + "WHERE p.profil =:profil")
                .setParameter("profil", profil)
                .getResultList();
    }

 
    @Override
    public ProfilRole getProfilRoles(Profil profil, Role role) {
        return (ProfilRole) getEntityManager()
                .createQuery("SELECT p FROM ProfilRole p "
                        + "WHERE p.profil=:profil AND p.role=:role")
                .setParameter("profil", profil)
                .setParameter("role", role)
                .getSingleResult();
    }

   
    @Override
    public boolean supProfilRoles(ProfilRole cRole) {
        try {
            em.createQuery("DELETE FROM ProfilRole pr WHERE pr.profil=:profil AND pr.role=:role")
                    .setParameter("profil", cRole.getProfil())
                    .setParameter("role", cRole.getRole())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
