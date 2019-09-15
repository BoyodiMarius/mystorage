/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author Armel
 */
@MappedSuperclass
public class BaseEntities implements Serializable {

    @Version
    @Column(name = "version", nullable = false)
    Integer version = 0;

}
