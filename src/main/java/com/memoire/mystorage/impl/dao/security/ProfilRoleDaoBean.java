/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.ProfilRoleDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Module;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.api.entities.security.ProfilRole;
import com.memoire.mystorage.api.entities.security.Role;
import com.memoire.mystorage.api.entities.security.RoleFonctionnalite;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilRoleDaoBean extends mystorageDaoBean<ProfilRole, Long> implements ProfilRoleDaoBeanLocal {

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
                        + "WHERE p.profil =:profil AND p.etat=:etat")
                .setParameter("profil", profil)
                .setParameter("etat", true)
                .getResultList();
    }

    @Override
    public List<RoleFonctionnalite> getFonctionnalites(Profil profil) {
        return getEntityManager()
                .createQuery("SELECT p FROM RoleFonctionnalite p WHERE p.role IN (SELECT pr.role FROM ProfilRole pr WHERE pr.profil=:profil AND pr.etat=:etat)")
                .setParameter("profil", profil)
                .setParameter("etat", true)
                .getResultList();
    }

    @Override
    public ProfilRole getProfilRoles(Profil profil, Role role) {
        return (ProfilRole) getEntityManager()
                .createQuery("SELECT p FROM ProfilRole p "
                        + "WHERE p.profil=:profil AND p.role=:role AND p.etat=:etat")
                .setParameter("profil", profil)
                .setParameter("role", role)
                .setParameter("etat", true)
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

    @Override
    public List<Role> getFonctionnaliteRoles() {
        return getEntityManager()
                .createQuery("SELECT p.role FROM RoleFonctionnalite p WHERE p.etat=:etat ")
                .setParameter("etat", true)
                .getResultList();
    }

    @Override
    public List<RoleFonctionnalite> getFonctionnalitesModules(Module modules) {
        return getEntityManager()
                .createQuery("SELECT p FROM RoleFonctionnalite p WHERE p.fonctionnalite IN (SELECT pm.fonctionnalite FROM FonctionnaliteModule pm WHERE pm.modules=:module AND pm.etat=:etat)")
                .setParameter("module", modules)
                .setParameter("etat", true)
                .getResultList();
    }

    @Override
    public List<ProfilRole> getProfilRoles1(Profil profil, Role role) {
        return getEntityManager()
                .createQuery("SELECT p FROM ProfilRole p "
                        + "WHERE p.profil=:profil AND p.role=:role AND p.etat=:etat")
                .setParameter("profil", profil)
                .setParameter("role", role)
                .setParameter("etat", true)
                .getResultList();
    }

}
