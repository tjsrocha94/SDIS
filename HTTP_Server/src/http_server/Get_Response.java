/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_server;

import java.net.*;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;
import java.util.Date;

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
    
    private ByteBuffer dataToSend;
    
    public ByteBuffer toSend;
    
    
    public Get_Response(long ID, SocketChannel _origin, int statusCode){
        this.ID = ID;
        this.origin = _origin;
        timeOfCreation = new Date();
        this.statusCode = statusCode;
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
    
    public ByteBuffer getDataToSend() {
        return dataToSend;
    }

    public void setDataToSend(ByteBuffer dataToSend) {
        this.dataToSend = dataToSend;
    }
    
    public void buildHeader() {
        


        if(this.statusCode == 200) {
            
            String content = new String();
            if (dataToSend.hasArray()) {
                content = new String( dataToSend.array() , Charset.forName("UTF-8"));
            }
        
            try {
                String header = 
                        "HTTP/1.1 200 OK" +
                        "\nDate: " + timeOfCreation.toString() +
                        "\nServer: " + InetAddress.getLocalHost().getHostName() +
                        "\nLast-Modified: " +
                        "\nAccept-Ranges: bytes" +
                        "\nContent-Length: " + content.length() +
                        "\nConnection: Close" +
                        "\nContent-Type: text/html; charset=UTF-8\n" + 
                        "\r\n" +
                        content;
                
                System.out.println("\n" + header + "\n");
                toSend = ByteBuffer.wrap( header.getBytes() );
                
                
            }    
                
            catch(UnknownHostException e) {}
        }
        else {
            try{
                String header = 
                        "HTTP/1.1 404 NOT FOUND" +
                        "\nDate: " + timeOfCreation.toString() +
                        "\nServer: " + InetAddress.getLocalHost().getHostName() +
                        "\nConnection: Close";
                
                System.out.println("\n" + header + "\n");
                toSend = ByteBuffer.wrap( header.getBytes() );
            }
            catch(UnknownHostException e) {}
        }
        
        
        /*
        if(statusCode == 200){
            
            try {
                
                return ("GET" + ' ' + "OK" + '\n'                            +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: " + InetAddress.getLocalHost().getHostName()   +
                        "Last-Modified: "                                       +
                        "Accept-Ranges: bytes"                                  +
                        "Content-Length: " + getDataToSend().capacity()              +
                        "Connection: Close"                                     +
                        "Content-Type: text/html; charset=UTF-8");
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
                
                return ("GET" + ' ' + "OK" + '\n'                            +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: "                                              +
                        "Last-Modified: "  +
                        "Accept-Ranges: bytes"                                  +
                        "Content-Length: " + getDataToSend().capacity()              +
                        "Connection: Close"                                     +
                        "Content-Type: text/html; charset=UTF-8");
            }
        }
        else{
            try {
                
                return ("GET" + ' ' + "Not found" + '\n'                     +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: " + InetAddress.getLocalHost().getHostName()   +
                        "Accept-Ranges: bytes"                                  +
                        "Connection: Close");
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
                
                return ("GET" + ' ' + "Not found" + '\n'                     +
                        "Date: " + timeOfCreation.toString()                    +
                        "Server: "                                              +
                        "Accept-Ranges: bytes"                                  +
                        "Connection: Close");
            }
        }
        
        
        */
        
        
    }
}

