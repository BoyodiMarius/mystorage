/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;


import com.memoire.mystorage.entities.Typemodule;
import com.memoire.mystorage.services.TypemoduleServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.UserTransaction;

/**
 *
 * @author Armel
 */
@Named(value = "TypemoduleBean")
@ViewScoped
public class TypemoduleBean implements Serializable {

    /**
     * Creates a new instance of ProfilBean
     */
    private Typemodule typemodule;
    private List<Typemodule> typemodules;


    @EJB
    private TypemoduleServiceBeanLocal tsbl;

    public TypemoduleBean() {
        this.typemodule = new Typemodule();
        this.typemodules = new ArrayList<>();
    }

    public void save(ActionEvent actionEvent) {
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
           
            if (this.typemodule.getId() == null) {
                this.tsbl.saveOne(typemodule);
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
            } else {
                this.tsbl.updateOne(typemodule);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }

        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
        this.typemodule = new Typemodule();
    }

    public void cancel(ActionEvent actionEvent) {
        this.typemodule = new Typemodule();
    }

    public void getObject(Integer id) {
        this.typemodule = this.tsbl.find(id);
    }

    public Typemodule getTypemodule() {
        return typemodule;
    }

    public void setTypemodule(Typemodule typemodule) {
        this.typemodule = typemodule;
    }

    public List<Typemodule> getTypemodules() {
        this.typemodules = this.tsbl.getAll();
        return typemodules;
    }

    public void setTypemodules(List<Typemodule> typemodules) {
        this.typemodules = typemodules;
    }

    public TypemoduleServiceBeanLocal getTsbl() {
        return tsbl;
    }

    public void setTsbl(TypemoduleServiceBeanLocal tsbl) {
        this.tsbl = tsbl;
    }

 

}
