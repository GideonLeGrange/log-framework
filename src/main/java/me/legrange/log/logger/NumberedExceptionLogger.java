package me.legrange.log.logger;

import static java.lang.String.format;
import me.legrange.log.Event;
import me.legrange.log.Logger;

/**
 * A logger implementation that numbers exceptions and logs them with the number to the default logger, while 
 * writing the stack trace to a file.
 * 
 * @author gideon
 */
public class NumberedExceptionLogger implements Logger {

    private final String fileName;
    private int msgId = 0; 

    public NumberedExceptionLogger(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public void log(Event entry) {
        if (entry.getThrowable().isPresent()) {
            int id = nextId();
            entry = new Event(entry.getMessage() + format(" [%d]", id), entry.getTimestamp(), entry.getLevel(), entry.getThrowable().get());
            writeToFile(id, entry.getThrowable().get());
        }
//        Log.log(entry);
    }
    
    private synchronized int nextId() { 
        msgId ++;
        return msgId;
    }
    
    private void writeToFile(int id, Throwable ex) {
        // put code to write to file here
        
    }
}
