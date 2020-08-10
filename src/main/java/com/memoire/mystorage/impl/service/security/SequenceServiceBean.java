/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.service.security;

import com.memoire.mystorage.api.dao.security.SequenceDaoBeanLocal;
import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Sequence;
import com.memoire.mystorage.api.service.security.SequenceServiceBeanLocal;
import com.memoire.mystorage.services.core.mystorageServiceBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
/**
 *
 * @author PaulAbram
 */
@Stateless
public class SequenceServiceBean extends mystorageServiceBean<Sequence, String> implements SequenceServiceBeanLocal {

    @EJB
    private SequenceDaoBeanLocal sdaol;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    protected mystorageDaoBeanLocal<Sequence, String> getDao() {
        return sdaol;
    }
}
