package httpmultithreadedserver;

import java.net.*;
import java.io.*;
import java.nio.*; 
import java.util.Arrays;


public class Worker extends Thread{
    private Socket socket = null;
    
    public Worker(Socket socket) {
        this.socket=socket;
        System.out.println("new connection ");
    }
    
    @Override
    public void run() {
       
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                
                BufferedReader in = new BufferedReader(new InputStreamReader(
                                                                            socket.getInputStream()));)
            {
            char[] array = new char[10000];
            String line;
            
            in.read(array,0,array.length);
            String str = new String(array);
            
            Get_Request request = new Get_Request(str,1);
            
            Get_Response response = request.createResponse();
 
            FileReader fileReader =  response.getPageReader();
            BufferedReader br = new BufferedReader(fileReader);
            
            while ((line = br.readLine()) != null) {
                out.write(line);
            }
            out.flush();
            out.close();
            
            
            socket.close();
        }catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        
    }
}
