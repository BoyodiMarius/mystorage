package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Promotion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-19T16:31:33")
@StaticMetamodel(Paiement.class)
public class Paiement_ extends BaseEntities_ {

    public static volatile SingularAttribute<Paiement, String> montantpaiement;
    public static volatile SingularAttribute<Paiement, String> modepaiement;
    public static volatile SingularAttribute<Paiement, String> typefrais;
    public static volatile SingularAttribute<Paiement, Inscription> inscription;
    public static volatile SingularAttribute<Paiement, Integer> id;
    public static volatile SingularAttribute<Paiement, Date> datepaiement;
    public static volatile SingularAttribute<Paiement, String> libellepaiement;
    public static volatile SingularAttribute<Paiement, Boolean> etat;
    public static volatile SingularAttribute<Paiement, Promotion> promotion;

}