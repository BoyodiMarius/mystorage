package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.ProfilRole;
import com.memoire.mystorage.entities.Utilisateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-07T00:23:48")
@StaticMetamodel(Profil.class)
public class Profil_ extends BaseEntities_ {

    public static volatile SetAttribute<Profil, Utilisateur> utilisateurs;
    public static volatile SetAttribute<Profil, ProfilRole> profilRoles;
    public static volatile SingularAttribute<Profil, String> libelle;
    public static volatile SingularAttribute<Profil, Integer> Id;
    public static volatile SingularAttribute<Profil, String> nom;

}