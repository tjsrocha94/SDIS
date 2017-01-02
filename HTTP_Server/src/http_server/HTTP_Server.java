

package http_server;

import java.util.*;
import java.util.LinkedList;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class HTTP_Server {
    
    private static ServerSocketChannel          server;
    private static Selector                     selector;
    private static LinkedList<Get_Request>      EventFIFO;
    private static long                          IDCounter = 0;

    
    
    public static void main(String[] args) {
        
        System.out.println("HTTP Event-based Server by Tiago Rocha, Tiago Oliveira and Vitor Lopes\nFEUP/DEEC/MIEEC/SDIS 2016-17");

        
        // SERVER INITIALIZATION :
        try{
            /* create a new socket for the server and set it as non-blocking socket
             * then bind the socket to the host and port
             * then create a selector and register the socket on the selector with selection key OP_ACCEPT
             * finally, create a linkedlist for the events
             */
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new java.net.InetSocketAddress("localhost",80));
            
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            
            EventFIFO = new LinkedList();
            
        }
        catch(IOException e){
            System.out.println("IOException thrown at ServerSocket creation: " + e);
        }
        
        System.out.println("Server has been initialized, listening on port 80.");
        
        
        while(true){
           
            /* LISTENER */
           /* check the server socket (must be non-blocking)
            * if there is a connection waiting to be accepted, accept it
            * if there is data to be read, read and create a request if the data is a GET request - associate the request to the client
            */
           listen();
           
           
           /* HANDLER */
           /* Then go trough the event list: for each event on the list, call the handler; the handler will deal with the event without blocking
            * (if it is necessary to get a file from disk, use non-blocking calls); the event handler sets the status of the event; if it needs
            * to read from disk, it makes a non-blocking call and sets the event status to WORKING; later, when it gets back to this event, checks
            * if the file is available; if it is, the handler creates a GET_Response event with the data to send and sets the status of the 
            * GET_Request as finished;
            *
            * If the event is of the GET_Response type and is pending, calls the associated client (Event.origin.socket) and sends the data; then
            * sets the event status as finished 
            */ 
           handle();
           
           /* CLEANER*/
           /* After the handler deals with the events, the cleaner comes and sweeps the finished events from the list 
            */
           clean();
           
           
           
        }  /* end of while loop */
        
        
        
    }; /* end of main */

    private static void listen() {
        
        int numberOfChannelsReady; // the number of channels waiting to be accepted
        
        try{
            
            numberOfChannelsReady = selector.selectNow();
            
            if (numberOfChannelsReady > 0) {
                
                SelectionKey key;
                
                Set<SelectionKey>       selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey>  keyIterator  = selectedKeys.iterator();
                
                while(keyIterator.hasNext()){
                    
                    key = keyIterator.next();
                    
                    if(key.isAcceptable() && key.isValid()) {
                        // a connection with a client is ready to be accepted
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("A new client has been accepted.");
                    }
                    if(key.isReadable() && key.isValid()){
                        
                        /* data arrived on one of the sockets, proceed to do a read on the data
                         * if the read result is -1, then the read failed - close the connection
                         * if not, process the data; if the data is a GET request, create a 
                         * pending event*/
                        
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        
                        if (client.read(buffer) >= 0) {
                            /* something to be read; if it is a request, create a GET_Request and
                             * allocate the client to the event; the request is later processed 
                             * by the handler and a response generated. The client is allocated
                             * to the response and the response can be sent */
                            
                            System.out.println("Incoming data from client: ");
                            
                            buffer.flip();
                            Charset charset=Charset.forName("ISO-8859-1");
                            CharsetDecoder decoder = charset.newDecoder();
                            CharBuffer charBuffer = decoder.decode(buffer);
                            System.out.println(charBuffer.toString());
                            
                            if ( checkRequest(charBuffer.toString()) ){
                            
                                try {
                                    Get_Request eventRequest = new Get_Request(client, charBuffer.toString(), IDCounter);
                                    IDCounter = IDCounter + 1;
                                    EventFIFO.add(eventRequest);
                                }
                                catch(NullPointerException e){
                                    System.out.println("NullPointerException thrown while adding event to LinkedList: " + e);
                                }
                            }
                            else System.out.println("Invalid request");
                                                        
                        }
                        else {
                            /* if a read on the socket goes wrong (-1), close the connection */
                            System.out.println("A connection has been closed.");
                            client.close();
                        }

                    }

                    keyIterator.remove();

                }  // end of while loop
                
                
            } // end of if
            
        }
        catch(IOException e){
            System.out.println("IOException thrown at Listen method: " + e);
        }
        
    };

    
    private static void handle() {
        
        /* Go through the event list; for each event: 
         * determine the type of event to handle; then proceed to work on it without blocking; if it needs to read a file, make the call, 
         * set the status of the event to WORKING and go to the next event; the next time it deals with the event, check if the file is ready
         * and create a response -- might not be possible, JAVA doesn't have non-blocking file reading/writing
         */
        
    };
    
    private static void clean(){
        
    };
    
    
    private static Boolean checkRequest(String request){
        
        String cmd, protocol;
        
        cmd = request.substring(0, request.indexOf(' '));
        
        if (cmd.compareTo("GET") != 0){
            System.out.println("On GET I got: " + cmd);
            return false;            
        }
        
        request = request.substring(request.indexOf(' ')+1);
        
        protocol = request.substring(request.indexOf(' ')+1, request.indexOf('\r'));
        
        if (protocol.compareTo("HTTP/1.1") != 0){
            System.out.println("On protocol I got: " + protocol);
            return false;            
        }
        
        return true;
        
    }
    
}
