/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.channels.SocketChannel;
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
    private File htmlpage;
    private FileReader pageReader;
    private int statusCode = 0;
    
    
    public Get_Response(long ID, SocketChannel _origin, String _protocol, File _htmlpage, int statusCode){
        
        this.ID = ID;
        this.origin = origin;
        timeOfCreation = new Date();
        this.statusCode = statusCode;
        
        protocol = _protocol;
        
        this.htmlpage = _htmlpage;
        
        try {
            this.pageReader = new FileReader(htmlpage);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public FileReader getPageReader(){
        
        return pageReader;
        
    }
    
    public int getStatusCode(){
        
        return statusCode;
        
    }
    
    public long getID(){
        
        return ID;
        
    }
}

