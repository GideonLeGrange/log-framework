package me.legrange.log.logger;

import me.legrange.log.Event;
import me.legrange.log.Logger;

/** 
 * A basic logger implementation that just prints to System.out. Really meant to be used
 * for testing and as last resort. 
 * @author gideon
 */
public class SystemOutLogger implements Logger {

    @Override
    public void log(Event entry) {
        System.out.printf("%s [%s]: %s\n", entry.getTimestamp(), entry.getLevel(), entry.getMessage());
        if (entry.getThrowable().isPresent()) {
            entry.getThrowable().get().printStackTrace(System.out);
        }
    }
    
}
