package me.legrange.log;

import java.util.Date;

/**
 * A log entry
 * @author gideon1  
 */
public interface Entry {
   
    String getMessage();
    
    Date getTimestamp();
    
    Level getLevel();
}
