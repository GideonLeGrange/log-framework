package me.legrange.log.logger;

import me.legrange.log.Event;
import me.legrange.log.Logger;

/** 
 * A basic logger implementation that prints to System.out, with exceptions going to System.err
 *
 * @author gideon
 */
public class ConsoleLogger implements Logger {

    @Override
    public void log(Event entry) {
        System.out.printf("%s [%s]: %s\n", entry.getTimestamp(), entry.getLevel(), entry.getMessage());
        if (entry.getThrowable().isPresent()) {
            entry.getThrowable().get().printStackTrace(System.err);
        }
    }
    
}
