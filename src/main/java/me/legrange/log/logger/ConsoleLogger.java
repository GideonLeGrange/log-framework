package me.legrange.log.logger;

import me.legrange.log.Event;
import me.legrange.log.Logger;

/** 
 * A basic logger implementation that prints to System.out, with exceptions going to System.err
 *
 * @author gideon
 */
public final class ConsoleLogger implements Logger {

    private final boolean useEmoji;

    public ConsoleLogger(boolean useEmoji) {
        this.useEmoji = useEmoji;
    }

    public ConsoleLogger() {
        this(false);
    }

    @Override
    public void log(Event entry) {
        System.out.printf(Standard.format(entry, useEmoji));
        if (entry.getThrowable().isPresent()) {
            entry.getThrowable().get().printStackTrace(System.err);
        }
    }
    
}
