package me.legrange.test.test1;

import me.legrange.log.Event;
import me.legrange.log.logger.SystemOutLogger;

/**
 *
 * @author gideon
 */
public class ShoutyLogger extends SystemOutLogger {

    @Override
    public void log(Event entry) {
        super.log(new Event(entry.getMessage().toUpperCase(), entry.getTimestamp(), entry.getLevel()));
    }
    
}
