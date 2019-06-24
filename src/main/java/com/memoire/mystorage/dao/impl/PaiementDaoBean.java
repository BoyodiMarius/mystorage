/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao.impl;


import com.memoire.mystorage.dao.PaiementDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Paiement;
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
public class PaiementDaoBean extends mystorageDaoBean<Paiement, Integer> implements PaiementDaoBeanLocal {

    public PaiementDaoBean(){
        
    }
    
    @Override
    public Class<Paiement> getType(){
        return Paiement.class;
        
    }
    
    @Override
    public List<Paiement> getPaie(Annee annee) {
        try {
            String jpql = "SELECT t FROM Paiement t where t.promotion.annee.id=:idannee";
            Query query = em.createQuery(jpql);
            query.setParameter("idannee", annee.getId());
            return query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
    
        
    @Override
    public List<Paiement> getPaie(Promotion promotion) {
        return getEntityManager()
                .createQuery("SELECT p FROM Paiement p WHERE p.promotion=:promotion")
                .setParameter("promotion", promotion)
                .getResultList();
    }

    }
