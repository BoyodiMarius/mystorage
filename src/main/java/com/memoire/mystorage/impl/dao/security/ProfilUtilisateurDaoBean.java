/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.ProfilUtilisateurDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilUtilisateurDaoBean extends mystorageDaoBean<ProfilUtilisateur, Long> implements ProfilUtilisateurDaoBeanLocal {

    public ProfilUtilisateurDaoBean() {
    }

    @Override
    public Class<ProfilUtilisateur> getType() {
        return ProfilUtilisateur.class;
    }

    @Override
    public List<Utilisateur> getUtilisateursProfile() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p,ProfilUtilisateur pi WHERE pi.utilisateurProfil = p")
                .getResultList();
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p NOT IN (SELECT pi.utilisateurProfil FROM ProfilUtilisateur pi WHERE pi.etat=:etat)")
                .setParameter("etat", false)
                .getResultList();
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfile() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p,ProfilUtilisateur pi WHERE pi.utilisateurProfil = p")
                .getResultList();
    }

    @Override
    public boolean supProfilUtilisateurs(ProfilUtilisateur profilUtilisateur) {
        try {
            em.createQuery("DELETE FROM ProfilUtilisateur pr WHERE pr.profil=:profil AND pr.utilisateurProfil=:utilisateur")
                    .setParameter("profil", profilUtilisateur.getProfil())
                    .setParameter("utilisateur", profilUtilisateur.getUtilisateurProfil())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
