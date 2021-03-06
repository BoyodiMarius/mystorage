/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.memoires.mystorage.managedeBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author BOYODI Wiyow Marius
 */
@ManagedBean
@ViewScoped
public class UploadFile implements Serializable{
    private String  source = "", source1;
    private UploadedFile image1 = null;
    private List<String> listeDossier;
    private List<Fichiers> liste;
    private Fichiers fic;
    

    public UploadFile() {
        
    }

    @PostConstruct
    public void chargerElement() {
        this.fic = new Fichiers();
        this.liste = new ArrayList();
        listeFile();
    }
    
   
        
    
    public void handleFileUpload(FileUploadEvent fileUploadEvent) throws IOException {
        String fileName = fileUploadEvent.getFile().getFileName();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        source1 = servletContext.getRealPath("");
        source = servletContext.getRealPath("") + File.separator  + File.separator + fileName;
        copyFileLocal(fileName, fileUploadEvent.getFile().getInputstream());
        transfert(fileName, source);
        delete(source);
       
    }

    public void copyFileLocal(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(source1 + File.separator + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            System.out.println("New file created!");
            source = source1 + File.separator + fileName;
            System.out.println("ma source: "+source);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            
        }
    }

    
     public  void transfert(String fileName, String source) {
        String server = "localhost";
        int port = 21;
        String user = "mystorage";
        String pass = "123456";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("MYSTORAGEDOCUMENTS");
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(source);
 
            String firstRemoteFile = fileName;
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile("/MYSTORAGEDOCUMENTS/"+firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
 
            // APPROACH #2: uploads second file using an OutputStream
//            File secondLocalFile = new File("E:/Test/Report.doc");
//            String secondRemoteFile = "test/Report.doc";
//            inputStream = new FileInputStream(secondLocalFile);
// 
//            System.out.println("Start uploading second file");
//            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
//            byte[] bytesIn = new byte[4096];
//            int read = 0;
// 
//            while ((read = inputStream.read(bytesIn)) != -1) {
//                outputStream.write(bytesIn, 0, read);
//            }
//            inputStream.close();
//            outputStream.close();
// 
//            boolean completed = ftpClient.completePendingCommand();
//            if (completed) {
//                System.out.println("The second file is uploaded successfully.");
//            }
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
    
    
     public void copyFile(String fileName, String source) {
        System.out.println("************");
          // get an ftpClient object    
        FTPClient ftpClient = new FTPClient();    
        FileInputStream inputStream = null;    
    
        try {    
            // pass directory path on server to connect    
            ftpClient.connect("localhost");    

            // pass username and password, returned true if authentication is    
            // successful    
            boolean login = ftpClient.login("mystorage", "123456");    
            ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
            if (login) {    
             System.out.println("Connection established...");    
             inputStream = new FileInputStream(source);    

             boolean uploaded = ftpClient.storeFile(fileName,    
               inputStream);    
             if (uploaded) {    
              System.out.println("File uploaded successfully !");    
             } else {    
              System.out.println("Error in uploading file !");    
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
    
    public void listeFile() {
        System.out.println("Liste");
          // get an ftpClient object    
        FTPClient ftpClient = new FTPClient();    
        FileInputStream inputStream = null;    
    
        try {    
            // pass directory path on server to connect    
            ftpClient.connect("localhost");    

            // pass username and password, returned true if authentication is    
            // successful    
            boolean login = ftpClient.login("mystorage", "123456");    
            ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
            if (login) {    
                FTPFile[] files1 = ftpClient.listFiles("/MYSTORAGEDOCUMENTS");
                //long size = 0;
                for(int i = 0;i<files1.length;i++){
                    System.out.println(files1[i].getName());
                    fic = new Fichiers();
                    fic.setNom(files1[i].getName());
                    fic.setSize(files1[i].getSize());
                    fic.setDateCreation("11/01/1996");
                    liste.add(fic);
                    //String n= files1[i].getName();
                    //listeDossier.add(n);
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
     
     
     public void delete(String source) {
        try{
            File file = new File(source);
            if(file.delete()){
                System.out.println(file.getName() + " est supprimé.");
            }else{
                System.out.println("Opération de suppression echouée");
            }
//            File dossier = new File("c:\\dossier_log");
//            if(dossier.delete()){
//                System.out.println(dossier.getName() + " est supprimé.");
//            }else{
//                System.out.println("Opération de suppression echouée");
//            }

     }catch(Exception e){
      e.printStackTrace();
     }
    }
     
     
     
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource1() {
        return source1;
    }

    public void setSource1(String source1) {
        this.source1 = source1;
    }

    public UploadedFile getImage1() {
        return image1;
    }

    public void setImage1(UploadedFile image1) {
        this.image1 = image1;
    }

    public List<String> getListeDossier() {
        return listeDossier;
    }

    public void setListeDossier(List<String> listeDossier) {
        this.listeDossier = listeDossier;
    }

    public List<Fichiers> getListe() {
        return liste;
    }

    public void setListe(List<Fichiers> liste) {
        this.liste = liste;
    }

    public Fichiers getFic() {
        return fic;
    }

    public void setFic(Fichiers fic) {
        this.fic = fic;
    }

   

   
    
    
    
}
