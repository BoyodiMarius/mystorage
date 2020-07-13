package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Profil;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-19T16:31:33")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ extends BaseEntities_ {

    public static volatile SingularAttribute<Utilisateur, String> ville;
    public static volatile SingularAttribute<Utilisateur, String> rue;
    public static volatile SingularAttribute<Utilisateur, String> question;
    public static volatile SingularAttribute<Utilisateur, String> pass;
    public static volatile SingularAttribute<Utilisateur, Profil> profil;
    public static volatile SingularAttribute<Utilisateur, String> photo;
    public static volatile SingularAttribute<Utilisateur, String> sexe;
    public static volatile SingularAttribute<Utilisateur, String> telephone;
    public static volatile SingularAttribute<Utilisateur, Boolean> actif;
    public static volatile SingularAttribute<Utilisateur, String> login;
    public static volatile SingularAttribute<Utilisateur, String> nom;
    public static volatile SingularAttribute<Utilisateur, Boolean> etat;
    public static volatile SingularAttribute<Utilisateur, String> reponse;
    public static volatile SingularAttribute<Utilisateur, String> codepostal;
    public static volatile SingularAttribute<Utilisateur, Long> id;
    public static volatile SingularAttribute<Utilisateur, String> lieunaiss;
    public static volatile SingularAttribute<Utilisateur, String> prenom;
    public static volatile SingularAttribute<Utilisateur, Date> datenaiss;
    public static volatile SingularAttribute<Utilisateur, String> email;
    public static volatile SingularAttribute<Utilisateur, String> situationmatrimoniale;
    public static volatile SingularAttribute<Utilisateur, Boolean> firstconnect;
    public static volatile SingularAttribute<Utilisateur, String> pays;

}