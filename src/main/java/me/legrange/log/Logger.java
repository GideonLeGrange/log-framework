package me.legrange.log;

/**
 * A logger that logs in a specific way. Implement this to manipulate how logged 
 * information is handled. 
 * 
 * @author gideon
 */
@SuppressWarnings("unused")
public interface Logger {
    
    /** Log a message with the given level 
     * 
     * @param entry The logged event
     */
    void log(Event entry);

    /** Return the logger name
     *
     * @return The name of the logger
     */
    default String getName() {
        return getClass().getSimpleName();
    }
}
