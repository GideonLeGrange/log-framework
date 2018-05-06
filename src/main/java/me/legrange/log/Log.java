package me.legrange.log;

import java.util.function.Supplier;
import static me.legrange.log.Level.CRITICAL;
import static me.legrange.log.Level.DEBUG;
import static me.legrange.log.Level.ERROR;
import static me.legrange.log.Level.INFO;
import static me.legrange.log.Level.WARNING;
import static java.lang.String.format;
import java.util.Date;

/**
 *
 * @author gideon
 */
public final class Log {
    
    /**
     * Log critical event.
     *
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void critical(String fmt, Object... args) {
        log(CRITICAL, fmt, args);
    }

    /**
     * Log error event.
     *
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void error(String fmt, Object... args) {
        log(ERROR, fmt, args);
    }

    /**
     * Log warning event.
     *
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void warning(String fmt, Object... args) {
        log(WARNING, fmt, args);
    }

    /**
     * Log informational event.
     *
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void info(String fmt, Object... args) {
        log(INFO, fmt, args);
    }

    /**
     * Log debug event.
     *
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void debug(String fmt, Object... args) {
        log(DEBUG, fmt, args);
    }

    /**
     * Log an event with the given level event.
     *
     * @param level The log level to use 
     * @param fmt The message format string
     * @param args The message arguments
     */
    private static void log(Level level, String fmt, Object... args) {
        log(level, () -> new Event(format(fmt, args), new Date(), level, null));
    }
    
    /** Log an event with an exception. 
    * @param level The log level to use 
     * @param fmt The message format string
     * @param args The message arguments
     * @param ex The exception that we're logging 
     */
    private static void log(Level level, Throwable ex, String fmt, Object...args) {
        log(level, () -> new Event(format(fmt, args), new Date(), level, ex));
    }

    private static void log(Level level, Supplier<Event> entry) {
        PackageLogger pl = Router.getInstance().route();
        if (level.code() <= pl.getLevel().code()) {
            String text;
            try {
                pl.getLogger().log(entry.get());
            } catch (Throwable ex) {
                StackTraceElement el = findErrorLine(ex);
                pl.getLogger().log(new Event(format("Error while logging at %s:%d: '%s'", el.getFileName(), el.getLineNumber(), ex.getMessage()), new Date(), ERROR, ex));
            }
        }
    }
    
    private static StackTraceElement findErrorLine(Throwable ex) {
        StackTraceElement[] els = ex.getStackTrace();
        int i = 0;
        boolean foundMe = false;
        while (i < els.length) {
            StackTraceElement el = els[i];
            String className = el.getClassName();
            if (className.startsWith(Log.class.getName())) {
                foundMe = true;
            } else {
                if (foundMe) {
                    return el;
                }
            }

            i++;
        }
        return null;
    }

}
