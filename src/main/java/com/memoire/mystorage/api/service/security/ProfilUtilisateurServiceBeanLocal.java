/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.service.security;

import com.memoire.mystorage.api.entities.security.ProfilUtilisateur;
import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrateur
 */
@Local
public interface ProfilUtilisateurServiceBeanLocal extends mystorageServiceBeanLocal<ProfilUtilisateur, Long> {

    public List<Utilisateur> getUtilisateursProfile();

    public List<Utilisateur> getUtilisateursNonProfil();

    public List<Utilisateur> getUtilisateursNonProfile();

    public boolean supProfilUtilisateurs(ProfilUtilisateur profilUtilisateur);
}
