/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.PromotionDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Annee;
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
public class PromotionDaoBean extends mystorageDaoBean<Promotion, Integer> implements PromotionDaoBeanLocal {

    public PromotionDaoBean(){
        
    }
    
    @Override
    public Class<Promotion> getType(){
        return Promotion.class;
        
    }
    
   
    @Override
    public List<Promotion> getPromotio(Annee annee) {
        try {
            String jpql = "SELECT t FROM Promotion t where t.annee.id=:idannee and t.etat=true" ;
            Query query = em.createQuery(jpql);
            query.setParameter("idannee",annee.getId());
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
       @Override
    public List<Promotion> getPromos(Annee annee) {
        try {
            String jpql = "SELECT t FROM Promotion t where t.annee.id=:idannee and t.etat=false" ;
            Query query = em.createQuery(jpql);
            query.setParameter("idannee",annee.getId());
            return query.getResultList();
        } catch (NoResultException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    
        @Override
    public List<Promotion> getVague(Integer idAnnee) {
        return getEntityManager()
                .createQuery("SELECT t FROM Promotion t WHERE t.annee.id="+ idAnnee )
                .getResultList();
    }

  
    }
