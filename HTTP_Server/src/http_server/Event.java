package http_server;

import java.nio.channels.*;
import java.util.Date;

public class Event {
  
    public enum type{GET_REQUEST, GET_RESPONSE};
    
    private type _type;
    private SocketChannel origin;
    private Date timeOfCreation;
    private String data;
    
    
    public Event(type _type, SocketChannel origin, String data){
        this._type = _type;
        this.origin = origin;
        this.data = data;
        timeOfCreation = new Date();
    };


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

    
    public int execGetRequest(){
        
        String page, pathName;
        
        page = data.substring(data.indexOf(' '), data.lastIndexOf(' '));
        
        pathName = "C:\Users\"
    }
    
}
