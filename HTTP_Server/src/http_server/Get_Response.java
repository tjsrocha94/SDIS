/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_server;

import java.io.FileReader;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class Get_Response {
    
    private int ID;
 //   private SocketChannel origin;
    private Date timeOfCreation;
    private String protocol;
    public FileReader pageReader;
    
    
    public Get_Response(int ID,/* SocketChannel _origin, */String _protocol, FileReader reader){
        
        this.ID = ID;
     //   this.origin = origin;
        timeOfCreation = new Date();
        
        protocol = _protocol;
        
        this.pageReader = reader;
    }
}