package za.co.adept.log.logger;

import za.co.adept.log.Event;
import za.co.adept.log.Logger;

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
