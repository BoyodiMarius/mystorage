/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.LogsDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Logs;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class LogsDaoBean extends mystorageDaoBean<Logs, Long> implements LogsDaoBeanLocal {

    public LogsDaoBean() {
    }

    @Override
    public Class<Logs> getType() {
        return Logs.class;
    }

    @Override
    public List<Logs> logs() {
        return getEntityManager()
                .createQuery("SELECT p FROM Logs p ORDER BY p.Id DESC")
                .setMaxResults(100)
                .getResultList();
    }
}
