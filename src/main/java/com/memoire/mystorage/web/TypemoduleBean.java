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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.transaction.UserTransaction;
import org.primefaces.event.FileUploadEvent;

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
    
      public void handleFileUpload(FileUploadEvent event) {
        try {
            System.out.println("pour la photo");
            String image = String.valueOf((int) (Math.random() * 10000000));
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images"+ File.separator + image + event.getFile().getFileName();
            InputStream inputStream = event.getFile().getInputStream();
           typemodule.setPhoto("/resources/images/" + image + event.getFile().getFileName());
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
