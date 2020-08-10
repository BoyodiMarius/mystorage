/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Utilisateur;
import com.project.resultview.web.utils.MethodeJournalisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import org.apache.log4j.Level;
import org.apache.shiro.SecurityUtils;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Administrateur
 */
@Named(value = "usersBean")
@ApplicationScoped
public class usersBean implements Serializable {

    private List<Map<String, ?>> fields;
    private List<Utilisateur> list;
    private Map<String, Object> mape;
    private boolean visible;
    private MethodeJournalisation journalisation;

    /**
     * Creates a new instance of usersBean
     */
    public usersBean() {
        this.fields = new ArrayList();
        this.list = new ArrayList<>();
        this.mape = new HashMap();
        this.journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        try {
            if (LoginBean.currentSubject().hasRole("Consulter Utilisateur Connectés")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(usersBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  usersConnect.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }
        } catch (Exception e) {
        }
    }

    public Date sessionTime() {
        return SecurityUtils.getSubject().getSession().getLastAccessTime();
    }

    public List<Utilisateur> getList() {
        return list;
    }

    public void setList(List<Utilisateur> list) {
        this.list = list;
    }

    public List<Map<String, ?>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, ?>> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getMape() {
        return mape;
    }

    public void setMape(Map<String, Object> mape) {
        this.mape = mape;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
