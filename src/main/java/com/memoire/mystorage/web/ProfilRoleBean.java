/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.services.ProfilRoleServiceBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Armel
 */
@Named(value = "ProfilRoleBean")
@ViewScoped
public class ProfilRoleBean implements Serializable{

    /**
     * Creates a new instance of EffectuerBean
     */
    private ProfilRole profilRole;
      private List<ProfilRole>ProfilRoles;
    
    @EJB
    private ProfilRoleServiceBeanLocal prsbl;
    public ProfilRoleBean() {
        this.profilRole = new ProfilRole();
    }
     public void save(){
        try {
            if(this.profilRole.getId() == null ){
                this.prsbl.saveOne(profilRole);
                System.out.println(profilRole);
            }else{
                this.prsbl.updateOne(profilRole);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public ProfilRoleBean(ProfilRole ProfileRole) {
        this.profilRole=profilRole ;
    }

    public ProfilRole getProfilRole() {
        return profilRole;
    }

    public List<ProfilRole> getProfilRoles() {
        return ProfilRoles;
    }

    public ProfilRoleServiceBeanLocal getPrsbl() {
        return prsbl;
    }

    public void setProfilRole(ProfilRole profilRole) {
        this.profilRole = profilRole;
    }

    public void setProfilRoles(List<ProfilRole> ProfilRoles) {
        this.ProfilRoles = ProfilRoles;
    }

    public void setPrsbl(ProfilRoleServiceBeanLocal prsbl) {
        this.prsbl = prsbl;
    }
    
}
