/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystorageManagedeBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.SocketException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author BOYODI Wiyow Marius
 */
@ManagedBean
@ViewScoped
public class TestMBean implements Serializable {

     private String  source = "", source1;
    
//    public TestMBean() {
//    }
    
    
    public void enregistrer() {
        System.out.println("test");
    }
    
    
    private StreamedContent file;
     
    public TestMBean() {        
//        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/loading.gif");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.gif");
    }
 
    public StreamedContent getFile() {
        return file;
    }
    
    
    public  void down() {
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect("localhost");
            ftpClient.login("mystorage", "123456");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
//            String remoteFile1 = "/MYSTORAGEDOCUMENTS/CV armel patrick.pdf";
//            //File downloadFile1 = new File("D:/Downloads/CV armel patrick.pdf");
//            File downloadFile1 = new File("D:/Downloads/CV armel patrick.pdf");
//            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
//            //InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/loading.gif");
//            //file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.gif");
//            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
//            outputStream1.close();
// 
//            if (success) {
//                System.out.println("File #1 has been downloaded successfully.");
//            }
 
            // APPROACH #2: using InputStream retrieveFileStream(String)
            String remoteFile2 = "/MYSTORAGEDOCUMENTS/CV armel patrick.pdf";
            //File downloadFile2 = new File("D:/Downloads/CV armel patrick.pdf");
            //OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            //InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/loading.gif");
            file = new DefaultStreamedContent(inputStream, "", "CV armel patrick.pdf");
//            byte[] bytesArray = new byte[4096];
//            int bytesRead = -1;
//            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//                outputStream2.write(bytesArray, 0, bytesRead);
//            }
// 
//            boolean success = ftpClient.completePendingCommand();
//            if (success) {
//                System.out.println("File #2 has been downloaded successfully.");
//            }
//            outputStream2.close();
//            inputStream.close();
 
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
    
    
     public  void download(String fichier) {
        FTPClient ftpClient = new FTPClient();
        try {
 
           // ftpClient.connect("localhost");
           ftpClient.connect("192.168.43.248");
            ftpClient.login("mystorage", "123456");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String remoteFile2 = "/MYSTORAGEDOCUMENTS/"+fichier;
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            file = new DefaultStreamedContent(inputStream, "", fichier);
 
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
    
    
    
    public void handleFileUpload(FileUploadEvent fileUploadEvent) throws IOException {
        String fileName = fileUploadEvent.getFile().getFileName();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        source1 = servletContext.getRealPath("");
        source = servletContext.getRealPath("") + File.separator  + File.separator + fileName;
        copyFile(fileName, fileUploadEvent.getFile().getInputstream());
       
    }

    public void copyFile(String fileName, InputStream in) {
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
    
    
}
