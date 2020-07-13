package com.memoire.mystorage.entities;

import com.memoire.mystorage.entities.Profil;
import com.memoire.mystorage.entities.ProfilRoleId;
import com.memoire.mystorage.entities.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-19T16:31:33")
@StaticMetamodel(ProfilRole.class)
public class ProfilRole_ extends BaseEntities_ {

    public static volatile SingularAttribute<ProfilRole, Role> role;
    public static volatile SingularAttribute<ProfilRole, Profil> profil;
    public static volatile SingularAttribute<ProfilRole, ProfilRoleId> id;

}