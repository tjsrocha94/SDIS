package httpmultithreadedserver;

import java.net.*;
import java.io.*;

public class HttpMultiThreadedServer {

    public static void main(String[] args) {
        
        if(args.length != 1) {
            System.out.println("Wrong number of arguments\n Program will now exit");
            System.exit(1);
        }
       
        int port = Integer.parseInt(args[0]);
        
        try (ServerSocket server = new ServerSocket(port);)
        {
            System.out.println("Server listenning on port " + args[0]);
            while(true) {
                new Worker(server.accept()).start();
            }
        }catch(IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    } 
}
