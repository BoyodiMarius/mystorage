/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoire.mystorage.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Gyldas
 */
public class SendSms {

    /**
     * @return the url_str
     */
    public static String getUrl_str() {
        return url_str;
    }

    /**
     * @param aUrl_str the url_str to set
     */
    public static void setUrl_str(String aUrl_str) {
        url_str = aUrl_str;
    }
    
    private String server;
    private String user;
    private String password;
    private String phonenumber;
    private String text;
    private String data;
    private String udh;
    private String pid;
    private String dcs;
    private String sender;
    private String validity;
    private String servicetype;
    private String smscroute;
    private String receiptrequested;
    private String sourceport;
    private String destport;
    private String delayuntil;
    private String voicemail;
    private String wapurl;
    private String wapsl;

    private static String url_str;

    public void init () {
        setServer(null);
        setUser(null);
        setPassword(null);
        setPhonenumber(null);
        setText(null);
        setData(null);
        setUdh(null);
        setPid(null);
        setDcs(null);
        setSender(null);
        setValidity(null);
        setServicetype(null);
        setSmscroute(null);
        setReceiptrequested(null);
        setSourceport(null);
        setDestport(null);
        setDelayuntil(null);
        setVoicemail(null);
        setWapurl(null);
        setWapsl(null);
    }

    public void setvar (String argname, String argvalue) {

       if (argname != null) {
          if (argvalue != null) {
                setUrl_str(getUrl_str() + "&" + argname + "=");
             try {
                String encoded = URLEncoder.encode (argvalue, "UTF-8");
                    setUrl_str(getUrl_str() + encoded);
             }
             catch (UnsupportedEncodingException e) {
                    setUrl_str(getUrl_str() + argvalue);
             }
          }
       }
           
    }

    public String send () {


       String returnstring;
   
       returnstring = null;

       if (getServer() == null) {
  	  System.out.println("sendsms.server value not set");
          return returnstring;
       }

        setUrl_str(getServer() + "?");
       setvar("user", getUser());
       setvar("password", getPassword());
       setvar("phonenumber", getPhonenumber());
       setvar("text", getText());
       setvar("data", getData());
       setvar("udh", getUdh());
       setvar("pid", getPid());
       setvar("dcs", getDcs());
       setvar("sender", getSender());
       setvar("validity", getValidity());
       setvar("servicetype", getServicetype());
       setvar("smscroute", getSmscroute());
       setvar("receiptrequested", getReceiptrequested());
       setvar("sourceport", getSourceport());
       setvar("destport", getDestport());
       setvar("delayuntil", getDelayuntil());
       setvar("voicemail", getVoicemail());
       setvar("wapurl", getWapurl());
       setvar("wapsl", getWapsl());

       try {
          URL url2=new URL(getUrl_str()); 

          HttpURLConnection connection = (HttpURLConnection) url2.openConnection(); 
          connection.setDoOutput(false); 
          connection.setDoInput(true); 

          String res=connection.getResponseMessage(); 
		
          System.out.println("Response Code  ->"+res); 
	
          int code = connection.getResponseCode () ; 
		
          if ( code == HttpURLConnection.HTTP_OK ) {
             //Get response data.
             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

             String str;
        
             while( null != ((str = in.readLine()))) {
                if (str.startsWith("MessageID=")) {  
                   returnstring = returnstring + str + "\r\n";
                   System.out.println(str);
                }
             }    
             connection.disconnect() ; 
          }
       }
       catch(IOException e) {
          System.out.println("unable to create new url"+e.getMessage());
       }
       return returnstring;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber the phonenumber to set
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the udh
     */
    public String getUdh() {
        return udh;
    }

    /**
     * @param udh the udh to set
     */
    public void setUdh(String udh) {
        this.udh = udh;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the dcs
     */
    public String getDcs() {
        return dcs;
    }

    /**
     * @param dcs the dcs to set
     */
    public void setDcs(String dcs) {
        this.dcs = dcs;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the validity
     */
    public String getValidity() {
        return validity;
    }

    /**
     * @param validity the validity to set
     */
    public void setValidity(String validity) {
        this.validity = validity;
    }

    /**
     * @return the servicetype
     */
    public String getServicetype() {
        return servicetype;
    }

    /**
     * @param servicetype the servicetype to set
     */
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    /**
     * @return the smscroute
     */
    public String getSmscroute() {
        return smscroute;
    }

    /**
     * @param smscroute the smscroute to set
     */
    public void setSmscroute(String smscroute) {
        this.smscroute = smscroute;
    }

    /**
     * @return the receiptrequested
     */
    public String getReceiptrequested() {
        return receiptrequested;
    }

    /**
     * @param receiptrequested the receiptrequested to set
     */
    public void setReceiptrequested(String receiptrequested) {
        this.receiptrequested = receiptrequested;
    }

    /**
     * @return the sourceport
     */
    public String getSourceport() {
        return sourceport;
    }

    /**
     * @param sourceport the sourceport to set
     */
    public void setSourceport(String sourceport) {
        this.sourceport = sourceport;
    }

    /**
     * @return the destport
     */
    public String getDestport() {
        return destport;
    }

    /**
     * @param destport the destport to set
     */
    public void setDestport(String destport) {
        this.destport = destport;
    }

    /**
     * @return the delayuntil
     */
    public String getDelayuntil() {
        return delayuntil;
    }

    /**
     * @param delayuntil the delayuntil to set
     */
    public void setDelayuntil(String delayuntil) {
        this.delayuntil = delayuntil;
    }

    /**
     * @return the voicemail
     */
    public String getVoicemail() {
        return voicemail;
    }

    /**
     * @param voicemail the voicemail to set
     */
    public void setVoicemail(String voicemail) {
        this.voicemail = voicemail;
    }

    /**
     * @return the wapurl
     */
    public String getWapurl() {
        return wapurl;
    }

    /**
     * @param wapurl the wapurl to set
     */
    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    /**
     * @return the wapsl
     */
    public String getWapsl() {
        return wapsl;
    }

    /**
     * @param wapsl the wapsl to set
     */
    public void setWapsl(String wapsl) {
        this.wapsl = wapsl;
    }
    
    
    
}