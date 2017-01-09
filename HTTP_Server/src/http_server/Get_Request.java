/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_server;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class Get_Request{

    
    public enum status{UNREAD, WAITING, CONCLUDED, FAIL};
    
    private status  state;
    private long    ID;
    private Date    timeOfCreation;
    private Path    path = null;
    private String  protocol;
    private int     statusCode = 0;
    
    private SocketChannel              origin;
    public AsynchronousFileChannel     fileChannel;
    public ByteBuffer                  buffer;
    public Future<Integer>             operation;
    

    
    
    public Get_Request(SocketChannel origin, String data, long ID){
        
        
        this.ID = ID;                   //Request identifier
        this.origin = origin;           //Origin (socket) of the request
        this.timeOfCreation = new Date();    //Time at which the request was created
        this.state = status.UNREAD;
                
        //Request's text processing
        data = data.substring(data.indexOf(' ')+1, data.indexOf('\r'));
        protocol = data.substring(data.indexOf(' ')+1);

        try{
            path = Paths.get(System.getProperty("user.dir"),Paths.get(data.substring(0, data.indexOf(' '))).toString());
            //System.out.println("Created a new request for the html file on path: " + path.toString());
            statusCode = 200;  //Success
            
        }
        catch (InvalidPathException e){
            
            //System.out.println("Page could not be found.\n" + e);
            statusCode = 404;  //Client Error - Page not found
            
        }
        
    }
    
    
    // List of setters and getters:
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
    
    public Path getPath(){
        return path;
    }
    
    public status getState() {
        return state;
    }
    
    public void setState(status state) {
        this.state = state;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    
}
