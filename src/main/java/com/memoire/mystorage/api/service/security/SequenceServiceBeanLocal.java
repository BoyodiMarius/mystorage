/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.api.service.security;

import com.memoire.mystorage.api.entities.security.Sequence;
import com.memoire.mystorage.services.core.mystorageServiceBeanLocal;
import javax.ejb.Local;

/**
 *
 * @author PaulAbram
 */
@Local
public interface SequenceServiceBeanLocal extends mystorageServiceBeanLocal<Sequence, String> {
    
}