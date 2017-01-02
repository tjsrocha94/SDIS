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
    
    private int ID;
 //   private SocketChannel origin;
    private Date timeOfCreation;
    private String protocol;
    public File htmlpage;
    public FileReader pageReader;
    
    
    public Get_Response(int ID,/* SocketChannel _origin, */String _protocol, File _htmlpage){
        
        this.ID = ID;
     //   this.origin = origin;
        timeOfCreation = new Date();
        
        protocol = _protocol;
        
        this.htmlpage = _htmlpage;
        
        try {
            this.pageReader = new FileReader(htmlpage);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Get_Response.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}