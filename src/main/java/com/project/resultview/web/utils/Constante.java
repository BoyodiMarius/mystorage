/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.resultview.web.utils;

import java.io.Serializable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author
 */
public abstract class Constante implements Serializable {

    public static final String ENREGISTREMENT_REUSSIE = "Enrégistrement réussie";
    public static final String ENREGISTREMENT_ECHOUE = "Echec de l'enrégistrement";

    public static final String ACTIVATION_REUSSIE = "Association réussie";
    public static final String DESACTIVATION_REUSSIE = "Désactivation réussie";

    public static final String ACTIVATION_ECHOUE = "Echec de l'Association";
    public static final String DESACTIVATION_ECHOUE = "Echec de la désactivation";

    public static final String DEMANDE_ECHOUE = "Echec de la demande";
    public static final String DEMANDE_REUSSIE = "Votre demande a été bien prise en compte";

    public static final String TRAITEMENT_ECHOUE = "Echec du traitement";
    public static final String TRAITEMENT_REUSSIE = "Traitement effectué avec succès";

    public static final String DECONNEXION_ECHOUE = "Déconnexion réussie";

    public static final String DESACTIVATION_ECHOU = "Echec de la désactivation du compte";
    public static final String DESACTIVATION_REUSSI = "Compte désactivé avec succès";

    public static final String ACTIVATION_ECHOU = "Echec de l'activation du compte";
    public static final String ACTIVATION_REUSSI = "Compte activé avec succès";

    public static final String MOT_DE_PASSE_DEFAUT = "admin";

    public static final String TRANSFERT_ECHOUE = "Echec du transfert";
    public static final String TRANSFERT_REUSSIE = "Transfert effectué avec succès";

    public static final String MODIFICATION_REUSSIE = "Echec de la modification";
    public static final String MODIFICATION_ECHOUE = "Modification effectué avec succès";

}
