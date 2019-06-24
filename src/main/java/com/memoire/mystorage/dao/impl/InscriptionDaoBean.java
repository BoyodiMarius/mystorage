/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.InscriptionDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Promotion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Armel
 */
@Stateless
public class InscriptionDaoBean extends mystorageDaoBean<Inscription, Integer> implements InscriptionDaoBeanLocal {

    public InscriptionDaoBean(){
        
    }
    
    @Override
    public Class<Inscription> getType(){
        return Inscription.class;
        
    }
    
    
    @Override
      public List<Inscription> getIns(Annee annee){
        try {
            String jpql = "SELECT t FROM Inscription t where t.promotion.annee.id=:idannee and t.etat=false";
            Query query = em.createQuery(jpql);
            query.setParameter("idannee", annee.getId());
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
      
       @Override
      public List<Inscription> getPay(Annee annee){
        try {
            String jpql = "SELECT t FROM Inscription t where t.promotion.annee.id=:idannee and t.etat=true";
            Query query = em.createQuery(jpql);
            query.setParameter("idannee", annee.getId());
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
      
    @Override
    public List<Inscription> getEta(Promotion promotion) {
        return getEntityManager()
                .createQuery("SELECT t FROM Inscription t WHERE t.promotion=:promotion")
                .setParameter("promotion", promotion)
                .getResultList();
    }
    }
