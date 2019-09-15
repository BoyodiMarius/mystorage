/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoires.mystorage.managedBean;

/**
 *
 * @author BOYODI Wiyow Marius
 */
public class FichierInformation  {
    private String nom;
    private String dateCreation;
    private long size;

    public FichierInformation() {
    }

    public FichierInformation(String nom, String dateCreation, long size) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.size = size;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    
    
}
