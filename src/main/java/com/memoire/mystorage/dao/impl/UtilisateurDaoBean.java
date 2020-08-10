/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;

import com.memoire.mystorage.dao.UtilisateurDaoBeanlocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class UtilisateurDaoBean extends mystorageDaoBean<Utilisateur ,Long > implements UtilisateurDaoBeanlocal {

    @Override
    public List<Utilisateur> getPersonnes() {
       return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p.profil IS NULL")
                .getResultList();}

    @Override
    public List<Utilisateur> getPersonnesProfil() {
        return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p.profil IS NOT NULL")
                .getResultList();
    }

    @Override
    public List<Utilisateur> getPersonnesNonProfil() {
      return getEntityManager()
                .createQuery("SELECT p FROM Utilisateur p WHERE p.actif=:actif AND p NOT IN (SELECT pa FROM Utilisateur pa)")
                .setParameter("actif", true)
                .getResultList();
    }
    
}
