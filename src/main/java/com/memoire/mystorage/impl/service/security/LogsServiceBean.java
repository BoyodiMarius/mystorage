/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.LogsDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Logs;
import com.memoire.mystorage.api.service.security.LogsServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrateur
 */
@Stateless
public class LogsServiceBean extends mystorageServiceBean<Logs, Long> implements LogsServiceBeanLocal {

    @EJB
    private LogsDaoBeanLocal ldbl;

    @Override
    protected mystorageDaoBeanLocal<Logs, Long> getDao() {
        return ldbl;
    }

    @Override
    public List<Logs> logs() {
        return ldbl.logs();
    }
}
