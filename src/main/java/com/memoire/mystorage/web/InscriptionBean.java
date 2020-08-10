/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;

import com.memoire.mystorage.dao.InscriptionDaoBeanLocal;
import com.memoire.mystorage.entities.Annee;
import com.memoire.mystorage.entities.Inscription;
import com.memoire.mystorage.entities.Paiement;
import com.memoire.mystorage.entities.Particulier;
import com.memoire.mystorage.api.entities.security.Profil;
import com.memoire.mystorage.entities.Promotion;
import com.memoire.mystorage.entities.Typemodule;
import com.memoire.mystorage.services.AnneeServiceBeanLocal;
import com.memoire.mystorage.services.InscriptionServiceBeanLocal;
import com.memoire.mystorage.services.PaiementServiceBeanLocal;
import com.memoire.mystorage.services.ParticulierServiceBeanLocal;
import com.memoire.mystorage.api.service.security.ProfilServiceBeanLocal;
import com.memoire.mystorage.services.PromotionServiceBeanLocal;
import com.memoire.mystorage.services.TypemoduleServiceBeanLocal;
import com.memoire.mystorage.transaction.TransactionManager;
import com.memoire.mystorage.utils.constantes.Constante;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Armel
 */
@Named(value = "InscriptionBean")
@ViewScoped
public class InscriptionBean implements Serializable {

    /**
     * Creates a new instance of UtilisateurBean
     */
    private Inscription inscription;
    private Annee annee;
    private Paiement paiement;
    private Particulier particulier;
    private Typemodule typemodule;
    private Promotion promotion;
    private List<Inscription> inscriptions;
    private List<Annee> annees, annet;
    private List<Paiement> paiements;
    private List<Particulier> particuliers, controleList;
    private List<Typemodule> typesmodules;
    private List<Promotion> promotions, prom, proms;
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private List<Paiement> paies;
    private List<Inscription> payers;
    private List<Inscription> inscris;
    private Profil collaborer;
    private Date dateNaiss;
    private String dateNaissance;

    @EJB
    private InscriptionServiceBeanLocal isbl;
    @EJB
    private InscriptionDaoBeanLocal idbl;
    @EJB
    private ParticulierServiceBeanLocal psbl;
    @EJB
    private TypemoduleServiceBeanLocal tsbl;
    @EJB
    private PromotionServiceBeanLocal psbl1;
    @EJB
    private PaiementServiceBeanLocal psbl2;
    @EJB
    private ProfilServiceBeanLocal psbl3;
    @EJB
    private AnneeServiceBeanLocal asbl;
    
    private String username="mystorageefficom@gmail.com";
    private String password="mystorage@2019";
    private String newPass="";

    public InscriptionBean() {
        this.inscription = new Inscription();
        this.collaborer = new Profil();
        this.particulier = new Particulier();
        this.paiement = new Paiement();
        this.typemodule = new Typemodule();
        this.promotion = new Promotion();
        this.annee = new Annee();
        this.inscriptions = new ArrayList<>();
        this.annees = new ArrayList<>();
        this.controleList = new ArrayList<>();
        this.paiements = new ArrayList<>();
        this.prom = new ArrayList<>();
        this.particuliers = new ArrayList<>();
        this.typesmodules = new ArrayList<>();
        this.promotions = new ArrayList<>();
        this.paies = new ArrayList<>();
        this.payers = new ArrayList<>();
        this.inscris = new ArrayList<>();
        this.proms = new ArrayList<>();
        this.annet = new ArrayList<>();
    }

    public void nouveau(ActionEvent actionEvent) {
        this.inscription = new Inscription();
    }

    @PostConstruct
    public void init() {
    }

   
    public boolean controleContact() {//true l'ajout peut etre effectué
        String contact = particulier.getPersonnePattern().getTelephone().trim();
        char premierCarater;
        boolean controle = false;
        if ((contact.length() == 11) && contact.matches("\\d{2}\\-\\d{2}\\-\\d{2}\\-\\d{2}")) {
            premierCarater = contact.charAt(0);
            if ((String.valueOf(premierCarater)).equals("9") || (String.valueOf(premierCarater)).equals("2")) {
                controle = true;
            } else {
                controle = false;
                FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "entrer un contact valide svp", "");
                FacesContext.getCurrentInstance().addMessage("", mf);
            }
        } else {
            controle = false;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "entrer un contact valide svp", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        return controle;
    }

    /**
     * Controle si tous les champs de l'interface sont remplis renvoie true si
     * un champ n'est pas rempli et false si tous les champs sont remplis
     *
     * @return
     */
    /**
     * renvoie true si le contact ou l email n existe pas
     *
     * @return
     */
    public boolean controleMailContact() {
        boolean ajouter = true;
        if (!particuliers.isEmpty()) {
            for (Particulier u : particuliers) {
                if (particulier.getPersonnePattern().getTelephone().equals(u.getPersonnePattern().getTelephone())) {
                    ajouter = false;
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "ce contact est lié à un compte", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);;
                }
                if (particulier.getPersonnePattern().getMail().equalsIgnoreCase(u.getPersonnePattern().getMail())) {
                    ajouter = true;
                    FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "ce email est lié à un compte", "");
                    FacesContext.getCurrentInstance().addMessage("", mf);
                }
            }
        }
        return ajouter;
    }
    
    public boolean controleMailEtContact() {
        boolean ajouter = false;
        this.particuliers = this.psbl.getAll();
        Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if (rfc2822.matcher(particulier.getPersonnePattern().getMail().trim()).matches()){
            if (!particuliers.isEmpty()) {
                for (Particulier u : particuliers) {
                    if (particulier.getPersonnePattern().getMail().equals(u.getPersonnePattern().getMail())) {
                        ajouter = true;
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Ce contact est lié à un compte", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);;
                    } else
                    if (particulier.getPersonnePattern().getMail().equalsIgnoreCase(u.getPersonnePattern().getMail())) {
                        ajouter = true;
                        FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Cet email est lié à un compte", "");
                        FacesContext.getCurrentInstance().addMessage("", mf);
                    }
                }
            }
        } else {
            ajouter = true;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cet email n'est pas valide", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        
        return ajouter;
    }

    public boolean controleVide() {
        System.out.println("je fais le controle du vide");
        boolean vide = false; // false ce n'est pas vide
        if (particulier.getPersonnePattern().getNom().trim().equals("")) {
            vide = true;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "le nom svp", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
//            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_FATAL,
//                    "le nom de l'entreprise", "");
//            FacesContext.getCurrentInstance().
//                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "test error", ""));
        }
        if (particulier.getPersonnePattern().getMail().trim().equals("")) {
            vide = true;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "l'email svp", "");
            FacesContext.getCurrentInstance().addMessage("", mf);;
        }
        if (particulier.getPersonnePattern().getTelephone().trim().equals("")) {
            vide = true;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "le contact svp", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        if (particulier.getLogin().trim().equals("")) {
            vide = true;
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "l'identifiant svp", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        return vide;
    }

    /**
     * **
     * true le nom n' existe pas
     *
     */
    private boolean controleExisteNom() {
        boolean ajouter = true;
        int i = 0;
        this.controleList = this.psbl.getAll();
        while (i < this.controleList.size()) {
            System.out.println("nom1" + particulier.getPersonnePattern().getNom().trim() + "nom2 " + controleList.get(i).getPersonnePattern().getNom());
            if (this.particulier.getPersonnePattern().getNom().trim().toLowerCase().equals(controleList.get(i).getPersonnePattern().getNom().trim().toLowerCase())) {
                System.out.println("Ce nom existe déjà");
//                Mtm.messageErrorPerso("Ce nom d'entreprise existe déjà dans la base");
                ajouter = false;
            }
            i++;
        }

        if (ajouter == false) {
            FacesMessage mf = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "ce nom existe déjà dans le système", "");
            FacesContext.getCurrentInstance().addMessage("", mf);
        }
        return ajouter;
    }

    public Date max() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, -15);
        return ca.getTime();
    }

    public void save() {
        if (controleMailEtContact()==true){
            System.out.println("existence");
        } else {
            
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            tx.begin();
            collaborer = this.psbl3.getOneBy("nom", "collaborer");
            this.psbl.saveOne(particulier);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
            Date date = formatter.parse(dateNaissance);
            System.out.println(this.particulier);
            this.inscription.setTypemodule(typemodule);
            this.inscription.setParticulier(particulier);
            this.isbl.saveOne(inscription);
//            String url = "par ce lien veuillez effectuez le paiement sécurisé" + " " + "/eformation/paiement.xhtml/";
//            String test = "CAGECFI SA VOUS REMERCIE POUR VOTRE INSCRIPTION ET VOUS INVITE a VITE VOUS INSCRIRE" + url;
//            SendMailByGlassfish.runTest(test, this.particulier.getEmail(), "MYSTORAGE", "Informations");
            
            //dossier(this.inscription.getParticulier().getNom());
            context.addMessage(null, new FacesMessage(Constante.INSCRIPTION_REUSSIT));
            tx.commit();
            this.inscription = new Inscription();
            this.particulier = new Particulier();
            this.typemodule = new Typemodule();
            

        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.INSCRIPTION_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
      
            this.inscription = new Inscription();
            this.particulier = new Particulier();
            this.typemodule = new Typemodule();
            }
    }

    public void saveAcceuil() {
        if (controleMailEtContact()==true){
            System.out.println("existence");
        } else {
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            tx.begin();
            collaborer = this.psbl3.getOneBy("nom", "utilisateur");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(dateNaissance);
            this.inscription.setTypemodule(typemodule);
            this.inscription.setParticulier(particulier);
            this.psbl.saveOne(particulier);
            this.isbl.saveOne(inscription);
//            String url = "par ce lien veuillez effectuez le paiement sécurisé" + " " + "/eformation/paiement.xhtml/";
//            String test = "CAGECFI SA VOUS REMERCIE POUR VOTRE INSCRIPTION ET VOUS INVITE a VITE VOUS INSCRIRE" + url;
//            SendMailByGlassfish.runTest(test, this.particulier.getEmail(), "MYSTORAGE", "Informations");
            
            //dossier(this.inscription.getParticulier().getNom());
            /*envoyer(this.inscription.getParticulier().getEmail(), "Bonjour Monsieur/Madame "
                +this.inscription.getParticulier().getNom()+
                " , Votre inscription à été pris en compte."
                        + "Cordialement, ");
            envoyer("mystorageefficom@gmail.com", "Nouvelle inscription à valider ");
            context.addMessage(null, new FacesMessage(Constante.INSCRIPTION_REUSSIT));*/
            tx.commit();
            this.inscription = new Inscription();
            this.particulier = new Particulier();
            this.typemodule = new Typemodule();
            

        } catch (Exception e) {
            e.getMessage();
            context.addMessage(null, new FacesMessage(Constante.INSCRIPTION_ECHOUE));
            try {
                tx.rollback();
            } catch (IllegalStateException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(InscriptionBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
      
            this.inscription = new Inscription();
            this.particulier = new Particulier();
            this.typemodule = new Typemodule();
         }
    }
    
    public void cancel(ActionEvent actionEvent) {
        this.inscription = new Inscription();
    }

    public void getObject(Integer id) {
        this.inscription = this.isbl.find(id);
        this.particulier = this.inscription.getParticulier();
        this.typemodule = this.inscription.getTypemodule();
        this.promotion = this.inscription.getPromotion();
        System.out.println(id);
    }

    public List<Particulier> lib(long id) {
        Particulier p = this.psbl.find(id);
        this.particuliers = this.psbl.getBy("id", id);
        return particuliers;

    }

    public List<Particulier> plus() {
        this.particuliers = this.psbl.getAll();
        return particuliers;
    }

    public void valider(Integer id) throws Exception {
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        this.inscription = this.isbl.find(id);
        System.out.println(this.inscription);
        this.inscription.getParticulier().setLogin(this.inscription.getParticulier().getPersonnePattern().getMail());
        this.inscription.getParticulier().setPasswd(new Sha256Hash("admin").toHex());
        this.inscription.setEtat(true);
        this.inscription.getParticulier().setProfilactif(true);
        this.isbl.updateOne(inscription);
        this.psbl.updateOne(this.inscription.getParticulier());
        context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
        newPass = "Bonjour Monsieur/Madame "
                +this.inscription.getParticulier().getPersonnePattern().getNom()+
                " , Votre compte à été valider. Voici vos paramètres de connexions :  Login : "
                +this.inscription.getParticulier().getPersonnePattern().getMail()+ " Mot de passe: admin";
        //envoyer(this.inscription.getParticulier().getEmail(), newPass);
        dossier(this.inscription.getParticulier().getPersonnePattern().getMail());
//        String log = "login" + "=" + "" + this.inscription.getParticulier().getEmail();
//        String pass = "password" + "=" + "admin";
//        String test = "CAGECFI SA VIENT DE VALIDER VOTRE INSCRIPTION.VOICI VOTRE" + " " + log + " " + "ET VOTRE" + pass + " " + "Cordialement";
//        SendMailByGlassfish.runTest(test, this.inscription.getParticulier().getEmail(), "MYSTORAGE", "Informations");
    }

    public void Rejeter(Integer id) throws Exception {
        UserTransaction tx = TransactionManager.getUserTransaction();
        FacesContext context = FacesContext.getCurrentInstance();
        this.inscription = this.isbl.find(id);
        System.out.println(this.inscription);
        this.psbl.updateOne(this.inscription.getParticulier());
        context.addMessage(null, new FacesMessage(Constante.MODIFICATION_REUSSIT));
//        String test = "CAGECFI SA VIENT DE REJETER VOTRE INSCRIPTION.VEUILLEZ VERIFIER VOS INFORMATIONS DE PAIEMENT.Cordialement";
//        SendMailByGlassfish.runTest(test, this.inscription.getEtudiant().getEmail(), "CAGECFI SA", "Informations");
    }

    public boolean checkIntConnection() {
        boolean status = false;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
        try {
            sock.connect(address, 3000);
            if (sock.isConnected()) {
                status = true;
            }
        } catch (Exception e) {

        } finally {
            try {
                sock.close();
            } catch (Exception e) {

            }
        }

        return status;
    }

    public void onAnneChange() {
        if (annee != null && !annee.equals("")) {
            proms.clear();
            proms.addAll(this.psbl1.getVague(annee.getId()));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            System.out.println("pour la foto");
            String image = String.valueOf((int) (Math.random() * 10000000));
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "image"
                    + File.separator + image + event.getFile().getFileName();
            InputStream inputStream = event.getFile().getInputStream();
            particulier.setPhoto("/resources/image/" + image + event.getFile().getFileName());
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
    
    
     public void envoyer(String email, String monMessage){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, 
                new javax.mail.Authenticator(){
                 @Override
                 protected PasswordAuthentication getPasswordAuthentication(){
                     return new PasswordAuthentication("mystorageefficom@gmail.com", "mystorage@2019");
                 }   
                }
        );
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mystorageefficom@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("No Reply");
            message.setText(monMessage);
            
            Transport.send(message);
            System.out.println("message envoyer");
            
        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
        
    }

     

    public void dossier(String nom) {

        // get an ftpClient object    
        FTPClient ftpClient = new FTPClient();

        try {

            // pass directory path on server to connect    
           ftpClient.connect("localhost");
            // ftpClient.connect("192.168.43.248");
            // pass username and password, returned true if authentication is    
            // successful    
            boolean login = ftpClient.login("mystorage", "123456");

            if (login) {

                System.out.println("Connection established...");

                boolean folderCreated = ftpClient.makeDirectory("/MYSTORAGEDOCUMENTS/" + nom);

                boolean folderCreatedpr = ftpClient.makeDirectory("/MYSTORAGEDOCUMENTS/" + nom + "/PRIVE");

                boolean folderCreatedpl = ftpClient.makeDirectory("/MYSTORAGEDOCUMENTS/" + nom + "/PUBLIC");

                if (folderCreated) {

                    System.out.println("Directory created successfully !");

                } else {

                    System.out.println("Error in creating directory !");

                }

                if (folderCreatedpr) {

                    System.out.println("Directory private created successfully !");

                } else {

                    System.out.println("Error in creating directory private !");

                }

                if (folderCreatedpl) {

                    System.out.println("Directory public created successfully !");

                } else {

                    System.out.println("Error in creating directory public !");

                }

                // logout the user, returned true if logout successfully    
                boolean logout = ftpClient.logout();

                if (logout) {

                    System.out.println("Connection close...");

                }

            } else {

                System.out.println("Connection fail...");

            }

        } catch (SocketException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                ftpClient.disconnect();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Particulier getParticulier() {
        return particulier;
    }

    public void setParticulier(Particulier particulier) {
        this.particulier = particulier;
    }

    public Typemodule getTypemodule() {
        return typemodule;
    }

    public void setTypemodule(Typemodule typemodule) {
        this.typemodule = typemodule;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public List<Inscription> getInscriptions() {
        this.inscris = this.isbl.getAll();
        
          for (Inscription inscription1 : inscris) {
            if (inscription1.isEtat() == false) {
                if (!this.inscriptions.contains(inscription1)) {
                    this.inscriptions.add(inscription1);
                }

            }
        }
        
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public List<Annee> getAnnees() {
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public List<Annee> getAnnet() {
        return annet;
    }

    public void setAnnet(List<Annee> annet) {
        this.annet = annet;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    public List<Particulier> getParticuliers() {
        return particuliers;
    }

    public void setParticuliers(List<Particulier> particuliers) {
        this.particuliers = particuliers;
    }

    public List<Particulier> getControleList() {
        return controleList;
    }

    public void setControleList(List<Particulier> controleList) {
        this.controleList = controleList;
    }

    public List<Typemodule> getTypesmodules() {
        this.typesmodules = this.tsbl.getAll();
        return typesmodules;
    }

    public void setTypesmodules(List<Typemodule> typesmodules) {
        this.typesmodules = typesmodules;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getProm() {
        return prom;
    }

    public void setProm(List<Promotion> prom) {
        this.prom = prom;
    }

    public List<Promotion> getProms() {
        return proms;
    }

    public void setProms(List<Promotion> proms) {
        this.proms = proms;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public List<Paiement> getPaies() {
        return paies;
    }

    public void setPaies(List<Paiement> paies) {
        this.paies = paies;
    }

    public List<Inscription> getPayers() {
        this.inscris = this.isbl.getAll();

        for (Inscription inscription1 : inscris) {
            if (inscription1.isEtat() == true) {
                if (!this.payers.contains(inscription1)) {
                    this.payers.add(inscription1);
                }

            }
        }
        return payers;

    }

    public void setPayers(List<Inscription> payers) {
        this.payers = payers;
    }

    public List<Inscription> getInscris() {
        return inscris;
    }

    public void setInscris(List<Inscription> inscris) {
        this.inscris = inscris;
    }

    public Profil getCollaborer() {
        return collaborer;
    }

    public void setCollaborer(Profil collaborer) {
        this.collaborer = collaborer;
    }

    public InscriptionServiceBeanLocal getIsbl() {
        return isbl;
    }

    public void setIsbl(InscriptionServiceBeanLocal isbl) {
        this.isbl = isbl;
    }

    public InscriptionDaoBeanLocal getIdbl() {
        return idbl;
    }

    public void setIdbl(InscriptionDaoBeanLocal idbl) {
        this.idbl = idbl;
    }

    public ParticulierServiceBeanLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(ParticulierServiceBeanLocal psbl) {
        this.psbl = psbl;
    }

    public TypemoduleServiceBeanLocal getTsbl() {
        return tsbl;
    }

    public void setTsbl(TypemoduleServiceBeanLocal tsbl) {
        this.tsbl = tsbl;
    }

    public PromotionServiceBeanLocal getPsbl1() {
        return psbl1;
    }

    public void setPsbl1(PromotionServiceBeanLocal psbl1) {
        this.psbl1 = psbl1;
    }

    public PaiementServiceBeanLocal getPsbl2() {
        return psbl2;
    }

    public void setPsbl2(PaiementServiceBeanLocal psbl2) {
        this.psbl2 = psbl2;
    }

    public ProfilServiceBeanLocal getPsbl3() {
        return psbl3;
    }

    public void setPsbl3(ProfilServiceBeanLocal psbl3) {
        this.psbl3 = psbl3;
    }

    public AnneeServiceBeanLocal getAsbl() {
        return asbl;
    }

    public void setAsbl(AnneeServiceBeanLocal asbl) {
        this.asbl = asbl;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    
    
}
