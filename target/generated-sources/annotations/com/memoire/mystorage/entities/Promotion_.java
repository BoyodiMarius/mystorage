package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Annee;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-10T00:41:46")
@StaticMetamodel(Promotion.class)
public class Promotion_ { 

    public static volatile SingularAttribute<Promotion, String> libelle;
    public static volatile SingularAttribute<Promotion, Date> datedebut;
    public static volatile SingularAttribute<Promotion, Date> datefin;
    public static volatile SingularAttribute<Promotion, Integer> id;
    public static volatile SingularAttribute<Promotion, Annee> annee;
    public static volatile SingularAttribute<Promotion, Boolean> etat;

}