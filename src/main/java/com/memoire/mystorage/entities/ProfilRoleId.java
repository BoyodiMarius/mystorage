/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Armel
 */
@Embeddable
public class ProfilRoleId implements Serializable {

    @Column(name = "profil", insertable = false, updatable = false)
    private Integer profil;

    @Column(name = "role", insertable = false, updatable = false)
    private Integer role;

    public ProfilRoleId() {
    }

    public ProfilRoleId(Integer Profil, Integer Role) {
        this.profil = Profil;
        this.role = Role;
    }

    public Integer getProfil() {
        return profil;
    }

    public Integer getRole() {
        return role;
    }

    public void setProfil(Integer Profil) {
        this.profil = Profil;
    }

    public void setRole(Integer Role) {
        this.role = Role;
    }

    @Override
    public String toString() {
        return "ProfilRoleId{" + "Profil=" + profil + ", Role=" + role + '}';
    }

}
