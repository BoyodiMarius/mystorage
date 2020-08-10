/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web.security.managedBeans;

import com.memoire.mystorage.api.entities.security.Logs;
import com.memoire.mystorage.api.service.security.LogsServiceBeanLocal;
import com.project.resultview.web.utils.MethodeJournalisation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Level;
import org.omnifaces.util.Ajax;

/**
 *
 * @author Administrateur
 */
@Named(value = "logsBean")
@ViewScoped
public class LogsBean implements Serializable {

    private List<Logs> logses;
    private List<Logs> logsesFilter;
    private boolean visible;
    private MethodeJournalisation journalisation;
    @EJB
    private LogsServiceBeanLocal lsbl;

    /**
     * Creates a new instance of LogsBean
     */
    public LogsBean() {
        this.logses = new ArrayList<>();
        this.journalisation = new MethodeJournalisation();
    }

    @PostConstruct
    public void init() {
        try {
            this.logses = this.lsbl.logs();
            if (LoginBean.currentSubject().hasRole("Consulter Historique")) {
                this.visible = true;
            } else {
                this.visible = false;
                journalisation.saveLog4j(LogsBean.class.getName(), Level.INFO, "Tentative d'accès non autrisée à la page:  historique.xhtml");
                this.journalisation = new MethodeJournalisation();
                Ajax.oncomplete("PF('error').show();");
            }
        } catch (Exception e) {
        }
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Logs l = (Logs) value;
        return l.getUtilisateurAction().getPersonnePattern().getNom().toLowerCase().contains(filterText)
                || l.getUtilisateurAction().getPersonnePattern().getPrenom().toLowerCase().contains(filterText)
                || l.getLogAction().toLowerCase().contains(filterText)
                || l.getLogDate().toString().equals(filterText)
                || l.getLogDateHeure().equals(filterText)
                || l.getLogRemoteIp().equals(filterText);
    }

    public List<Logs> getLogses() {
        return logses;
    }

    public void setLogses(List<Logs> logses) {
        this.logses = logses;
    }

    public LogsServiceBeanLocal getLsbl() {
        return lsbl;
    }

    public void setLsbl(LogsServiceBeanLocal lsbl) {
        this.lsbl = lsbl;
    }

    public List<Logs> getLogsesFilter() {
        return logsesFilter;
    }

    public void setLogsesFilter(List<Logs> logsesFilter) {
        this.logsesFilter = logsesFilter;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
