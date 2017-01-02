/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class Get_Request{
    
    private int ID;
    //private SocketChannel origin;
    private Date timeOfCreation;
    private Path path;
    private String protocol;
    private byte[] page;

    
    
    public Get_Request(/*SocketChannel origin, */String data, int ID){
        
        this.ID = ID;
        //this.origin = origin;
        timeOfCreation = new Date();
                
        protocol = data.substring(data.lastIndexOf(' '));
        
        try{
            path = Paths.get(System.getProperty("user.dir"),Paths.get(data.substring(data.indexOf(' ')+1, data.lastIndexOf(' '))).toString());
            System.out.println("Path: " + path.toString());
            
        }
        catch (InvalidPathException e){
            
            System.out.println("Pedido inválido\n" + e);
            
        }
        
    }
    
 /*   public SocketChannel getOrigin() {
        return origin;
    }

    public void setOrigin(SocketChannel origin) {
        this.origin = origin;
    }
*/    
    public Date getTimeOfCreation() {
        return timeOfCreation;
    }
    
    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
    
    public Get_Response createResponse(){
        
        byte[] page;
        
        
        try{
             
            page = Files.readAllBytes(path.toAbsolutePath());
            
            return new Get_Response(ID,/* origin, */protocol, page);
            

        }
        catch(IOException e){
            
            System.out.println("Erro de leitura do ficheiro\n" + e);
            return null;
            
        }
    }
    
}