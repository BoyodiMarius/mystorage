/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.impl.dao.security;

import com.memoire.mystorage.api.dao.security.SequenceDaoBeanLocal;
import com.memoire.mystorage.api.entities.security.Sequence;
import com.memoire.mystorage.dao.core.mystorageDaoBean;
import javax.ejb.Stateless;

/**
 *
 * @author PaulAbram
 */
@Stateless
public class SequenceDaoBean extends mystorageDaoBean<Sequence, String>implements SequenceDaoBeanLocal {

    public SequenceDaoBean() {
        super(Sequence.class);
    }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
