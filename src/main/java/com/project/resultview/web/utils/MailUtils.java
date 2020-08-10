/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.resultview.web.utils;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author elvis
 */
public class MailUtils {

    private static String sujet = "Demande de chequier Eco";

    private static String destinataire = "kossi.adjo@laposte.tg";

    private static String copie = "kossi.adjo@laposte.tg";

//                    String destinataire  = "ccp@laposte.tg";
//
//                    String  copie = "pidenam@laposte.tg";
    
    public static void sendMessage(String text) {
        // 1 -> Création de la session 
        System.out.println("------------envoi mail-----------------");
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "mail.laposte.tg"); //SMTP_HOST1
        properties.setProperty("mail.smtp.user", "sms.contact@laposte.tg"); //LOGIN_SMTP1
        properties.setProperty("mail.from", "sms.contact@laposte.tg"); //IMAP_ACCOUNT1
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties);
        // 1 -> Création de la session
        MimeMessage message = new MimeMessage(session);
        try {
            message.setText(text);
            message.setSubject(MailUtils.sujet);
            message.addRecipients(Message.RecipientType.TO, MailUtils.destinataire);
            message.addRecipients(Message.RecipientType.CC, MailUtils.copie);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 3 -> Envoi du message
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect("sms.contact@laposte.tg", "sms.contact,@2016"); //LOGIN_SMTP1, PASSWORD_SMTP1
            transport.sendMessage(message, new Address[]{new InternetAddress(MailUtils.destinataire),
                new InternetAddress(MailUtils.copie)});
            System.out.println("------------mail envoyé-----------------");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
