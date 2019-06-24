/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.dao;



import com.memoire.mystorage.dao.core.mystorageDaoBeanLocal;
import com.memoire.mystorage.entities.Role;
import java.io.Serializable;
import javax.ejb.Local;

/**
 *
 * @author Armel
 */
@Local
public interface RoleDaoBeanLocal extends mystorageDaoBeanLocal<Role, Integer>{
    
}
