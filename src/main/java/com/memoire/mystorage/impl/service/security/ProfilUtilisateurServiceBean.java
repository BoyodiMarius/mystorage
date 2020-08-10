/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.ProfilUtilisateurDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.api.service.security.ProfilUtilisateurServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class ProfilUtilisateurServiceBean extends mystorageServiceBean<ProfilUtilisateur, Long> implements ProfilUtilisateurServiceBeanLocal {

    @EJB
    private ProfilUtilisateurDaoBeanLocal pudbl;

    @Override
    protected mystorageDaoBeanLocal<ProfilUtilisateur, Long> getDao() {
        return pudbl;
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfil() {
        return this.pudbl.getUtilisateursNonProfil();
    }

    @Override
    public List<Utilisateur> getUtilisateursProfile() {
        return this.pudbl.getUtilisateursProfile();
    }

    @Override
    public List<Utilisateur> getUtilisateursNonProfile() {
        return this.pudbl.getUtilisateursNonProfile();
    }

    @Override
    public boolean supProfilUtilisateurs(ProfilUtilisateur profilUtilisateur) {
        return this.pudbl.supProfilUtilisateurs(profilUtilisateur);
    }

}
