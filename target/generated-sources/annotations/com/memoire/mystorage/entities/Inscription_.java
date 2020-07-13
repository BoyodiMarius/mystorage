package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.entities.Typemodule;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-19T16:31:33")
@StaticMetamodel(Inscription.class)
public class Inscription_ extends BaseEntities_ {

    public static volatile SingularAttribute<Inscription, Integer> nbretranches;
    public static volatile SingularAttribute<Inscription, Typemodule> typemodule;
    public static volatile SingularAttribute<Inscription, Particulier> particulier;
    public static volatile SingularAttribute<Inscription, Date> datebebinscription;
    public static volatile SingularAttribute<Inscription, Date> datefninscription;
    public static volatile SingularAttribute<Inscription, Integer> id;
    public static volatile SingularAttribute<Inscription, Annee> annee;
    public static volatile SingularAttribute<Inscription, Date> dateinscription;
    public static volatile SingularAttribute<Inscription, Boolean> etat;
    public static volatile SingularAttribute<Inscription, Promotion> promotion;

}