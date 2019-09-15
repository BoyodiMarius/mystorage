/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.services.impl;


import com.memoire.mystorage.dao.UtilisateurDaoBeanlocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Utilisateur;
import com.memoire.mystorage.services.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Armel
 */
@Stateless
public class UtilisateurServiceBean extends mystorageServiceBean<Utilisateur, Long> implements UtilisateurServiceBeanLocal {
 @EJB
 private UtilisateurDaoBeanlocal udbl;
 
 @Override
 protected mystorageDaoBeanLocal<Utilisateur, Long> getDao(){
     return udbl;
 }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 @Override
     public List<Utilisateur> getPersonnesProfil(){
         return this.udbl.getPersonnesProfil();
     }
     
     
    @Override
     public List<Utilisateur> getPersonnes(){
         return this.udbl.getPersonnes();
     }
    
     
    @Override
     public List<Utilisateur> getPersonnesNonProfil(){
         return this.udbl.getPersonnesNonProfil();
     }
}
