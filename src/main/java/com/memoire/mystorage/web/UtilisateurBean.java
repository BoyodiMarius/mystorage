/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;

import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.Utilisateur;
import com.memoire.mystorage.services.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.UtilisateurServiceBeanLocal;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Armel
 */
@Named(value = "UtilisateurBean")
@ViewScoped
public class UtilisateurBean implements Serializable {

    /**
     * Creates a new instance of UtilisateurBean
     */
    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateurs;
    private List<Utilisateur> girls;
    private List<Utilisateur> boys;
    private boolean skip;
    private String space = "  ";
    private List<Utilisateur> utilisateurs1;
    
    private Date date = new Date();
    private Profil profil;
    private List<Profil> profils;
    @EJB
    private UtilisateurServiceBeanLocal usbl;
    @EJB
    private ProfilServiceBeanLocal psbl1;
    
    public UtilisateurBean() {
        this.utilisateur = new Utilisateur();
        this.profil = new Profil();
        this.profils = new ArrayList<>();
        this.utilisateurs1 = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
        this.boys = new ArrayList<>();
        this.girls = new ArrayList<>();
        
    }
    
    public void cancel(ActionEvent actionEvent) {
        this.utilisateur = new Utilisateur();
    }
    
    public void save(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.utilisateur.getId() == null) {
                this.utilisateur.setLogin((this.utilisateur.getEmail()));
                 this.utilisateur.setPass(new Sha256Hash("admin").toHex());
                this.usbl.saveOne(utilisateur);
                context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
            } else {
                this.usbl.updateOne(utilisateur);
                context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
            }
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
    }
    
    public Date max() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR,-20);
        return ca.getTime();
    }
    
    public void getObject(Long id) {
        this.utilisateur = this.usbl.find(id);
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try {
            System.out.println("pour la foto");
            String image = String.valueOf((int) (Math.random() * 10000000));
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "image"
                    + File.separator + image + event.getFile().getFileName();
            InputStream inputStream = event.getFile().getInputstream();
            utilisateur.setPhoto("/resources/image/" + image + event.getFile().getFileName());
            ImageOutputStream out = new FileImageOutputStream(new File(newFileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
    
    public void associerPoste() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for (Utilisateur utilisateur1 : utilisateurs1) {
                this.usbl.updateOne(utilisateur);
            }
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
    }
    
    public void associerProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for (Utilisateur utilisateur1 : utilisateurs1) {
              utilisateur1.setProfil(this.profil);
                this.usbl.updateOne(utilisateur1);
            }
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_REUSSIT));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(Constante.ENREGISTREMENT_ECHOUE));
        }
        this.utilisateur = new Utilisateur();
        this.profil = new  Profil();
    }
    
    public List<Utilisateur> personnesProfil() {
        List<Utilisateur> us = this.usbl.getAll();
        List<Utilisateur> us1 = new ArrayList<>();
        for (Utilisateur us11 : us) {
            if (us11.getProfil() != null) {
                us1.add(us11);
            }
        }
        return us1;
    }
    
    public List<Utilisateur> personnesNonProfil() {
        return this.usbl.getPersonnesProfil();
    }
    
    public void personnesSelectProfil() {
        for (Utilisateur utilisateur1 : personnesNonProfil()) {
            utilisateurs1.add(utilisateur1);
        }
    }
    
    public void activerUtilisateur(Long u) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u2 = new Utilisateur();
            u2 = us.get(0);
            u2.setActif(true);
            this.usbl.updateOne(u2);
            context.addMessage(null, new FacesMessage("activé"));
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    public void desactiverUtilisateur(Long u) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Utilisateur> us = this.usbl.getBy("id", u);
            Utilisateur u2 = new Utilisateur();
            u2 = us.get(0);
            u2.setActif(false);
            this.usbl.updateOne(u2);
            context.addMessage(null, new FacesMessage("désactivé"));
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    public List<Utilisateur> utilisateurActif() {
        List<Utilisateur> list1 = this.usbl.getPersonnesNonProfil();
        List<Utilisateur> us = new ArrayList<>();
        for (Utilisateur u : list1) {
            if (!list1.contains(u)) {
                list1.add(u);
            }
        }
        for (Utilisateur u : list1) {
            if (u.isActif() == true) {
                us.add(u);
            }
        }
        
        return us;
    }
    
    public List<Utilisateur> utilisateurInactif() {
        List<Utilisateur> list1 = this.usbl.getPersonnesNonProfil();
        List<Utilisateur> us = new ArrayList<>();
        for (Utilisateur u : list1) {
            if (!list1.contains(u)) {
                list1.add(u);
            }
        }
        for (Utilisateur u : list1) {
            if (u.isActif() == false) {
                us.add(u);
            }
        }
        
        return us;
    }
    
    public void modifierutilisateurProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.utilisateur.setProfil(profil);
            this.usbl.updateOne(utilisateur);
            context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    public boolean checkIntConnection() {
        boolean status = false;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        try {
            sock.connect(address, 3000);
            if (sock.isConnected()) {
                status = true;
            }
        } catch (Exception e) {
            
        } finally {
            try {
                sock.close();
            } catch (Exception e) {
                
            }
        }
        
        return status;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<Utilisateur> getGirls() {
        return girls;
    }

    public void setGirls(List<Utilisateur> girls) {
        this.girls = girls;
    }

    public List<Utilisateur> getBoys() {
        return boys;
    }

    public void setBoys(List<Utilisateur> boys) {
        this.boys = boys;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public List<Utilisateur> getUtilisateurs1() {
        return utilisateurs1;
    }

    public void setUtilisateurs1(List<Utilisateur> utilisateurs1) {
        this.utilisateurs1 = utilisateurs1;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public UtilisateurServiceBeanLocal getUsbl() {
        return usbl;
    }

    public void setUsbl(UtilisateurServiceBeanLocal usbl) {
        this.usbl = usbl;
    }

    public ProfilServiceBeanLocal getPsbl1() {
        return psbl1;
    }

    public void setPsbl1(ProfilServiceBeanLocal psbl1) {
        this.psbl1 = psbl1;
    }

    
   
}
