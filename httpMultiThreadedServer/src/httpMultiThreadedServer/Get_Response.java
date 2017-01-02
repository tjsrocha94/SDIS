/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpMultiThreadedServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class Get_Response {
    
    private long ID;
    private SocketChannel origin;
    private Date timeOfCreation;
    private String protocol;
    private int statusCode = 0;
    private File htmlPage;
    private FileReader pageReader;
    
    
    public Get_Response(long ID, SocketChannel _origin, String _protocol, File _htmlpage, int statusCode){
        
        this.ID = ID;
        this.origin = origin;
        timeOfCreation = new Date();
        this.statusCode = statusCode;
        protocol = _protocol;
        this.htmlPage = _htmlpage;
        
        try {
            pageReader = new FileReader(htmlPage);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
            statusCode = 404;
        }
    }    
    
    public SocketChannel getOrigin() {
        return origin;
    }

    public void setOrigin(SocketChannel origin) {
        this.origin = origin;
    }
    
    public Date getTimeOfCreation() {
        return timeOfCreation;
    }
    
    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
    
    public int getStatusCode(){
        
        return statusCode;
        
    }
    
    public long getID(){
        
        return ID;
        
    }
    
    public FileReader getPageReader(){
        
        return pageReader;
        
    }
    
    public String buildHeader(File htmlpage){
        
        String statusMessage;
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        if(statusCode == 200){
            
            try {
                
                return (protocol + ' ' + "OK" + '\n'                            +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: " + InetAddress.getLocalHost().getHostName()   +
                        "Last-Modified: " + sdf.format(htmlpage.lastModified()) +
                        "Accept-Ranges: bytes"                                  +
                        "Content-Length: " + htmlpage.length()                  +
                        "Connection: Close"                                     +
                        "Content-Type: text/html; charset=UTF-8");
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
                
                return (protocol + ' ' + "OK" + '\n'                            +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: "                                              +
                        "Last-Modified: " + sdf.format(htmlpage.lastModified()) +
                        "Accept-Ranges: bytes"                                  +
                        "Content-Length: " + htmlpage.length()                  +
                        "Connection: Close"                                     +
                        "Content-Type: text/html; charset=UTF-8");
            }
        }
        else{
            try {
                
                return (protocol + ' ' + "Not found" + '\n'                     +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: " + InetAddress.getLocalHost().getHostName()   +
                        "Accept-Ranges: bytes"                                  +
                        "Connection: Close");
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
                
                return (protocol + ' ' + "Not found" + '\n'                     +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: "                                              +
                        "Accept-Ranges: bytes"                                  +
                        "Connection: Close");
            }
        }
        
        
        
        
    }
}

