package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Annee;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-24T14:44:51")
@StaticMetamodel(Promotion.class)
public class Promotion_ extends BaseEntities_ {

    public static volatile SingularAttribute<Promotion, String> libelle;
    public static volatile SingularAttribute<Promotion, Date> datedebut;
    public static volatile SingularAttribute<Promotion, Date> datefin;
    public static volatile SingularAttribute<Promotion, Integer> id;
    public static volatile SingularAttribute<Promotion, Annee> annee;
    public static volatile SingularAttribute<Promotion, Boolean> etat;

}