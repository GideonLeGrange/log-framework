package za.co.adept.log;

import java.util.function.Supplier;
import static za.co.adept.log.Level.CRITICAL;
import static za.co.adept.log.Level.DEBUG;
import static za.co.adept.log.Level.ERROR;
import static za.co.adept.log.Level.INFO;
import static za.co.adept.log.Level.WARNING;
import static java.lang.String.format;
import java.util.Date;

/**
 * The log interface. Use this class to add logging to your code.
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
     * Log an exception as a critical error.
     *
     * @param ex The exception
     */
    public static void critical(Throwable ex) {
        log(CRITICAL, ex, ex.getMessage());
    }

    /**
     * Log an exception as a critical error.
     *
     * @param ex The exception
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void critical(Throwable ex, String fmt, Object... args) {
        log(CRITICAL, ex, ex.getMessage(), fmt, args);
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
     * Log an exception as an error.
     *
     * @param ex The exception
     */
    public static void error(Throwable ex) {
            log(ERROR, ex, ex.getMessage());
    }

    /**
     * Log an exception as an error.
     *
     * @param ex The exception
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void error(Throwable ex, String fmt, Object... args) {
        log(ERROR, ex, fmt, args);
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
     * Log an exception as a warning.
     *
     * @param ex The exception
     */
    public static void warning(Throwable ex) {
        log(WARNING, ex, ex.getMessage());
    }

    /**
     * Log an exception as a warning.
     *
     * @param ex The exception
     * @param fmt The message format string
     * @param args The message arguments
     */
    public static void warning(Throwable ex, String fmt, Object... args) {
        log(WARNING, ex, ex.getMessage(), fmt, args);
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
     * Log a debug event, while avoiding any expensive code unless the logging
     * occurs. Use this when part of the building is expensive operations.
     *
     * @param msg A supplier that will provide the debug message when required.
     */
    public static void debug(Supplier<String> msg) {
        log(DEBUG, () -> new Event(msg.get(), new Date(), DEBUG, null));
    }
    
    /** Set the default application logger. Only call this from your application's main code. This
     * must not be called by libraries. 
     * 
     * @param logger 
     */
    public static void setDefaultLogger(Logger logger) {
        Router.getInstance().setDefaultLogger(logger);
    }
    
    /** Set the default logging level. Only call this from your application's main code. This
     * must not be called by libraries. 
     * 
     * @param level The default level to set.
     */
    public static void setDefaultLevel(Level level) {
        Router.getInstance().setDefaultLevel(level);
    }
    
    /** Set the logger for the package calling the logger. 
     * 
     * @param logger
     */
    public static void setLogger(Logger logger) {
        Router.getInstance().setLogger(logger);
    }
    
    
    /** Set the log level for the package calling the logger. 
     * 
     * @param level The log level
     */
    public static void setLevel(Level level) {
        Router.getInstance().setLevel(level);
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

    /**
     * Log an event with an exception.
     *
     * @param level The log level to use
     * @param fmt The message format string
     * @param args The message arguments
     * @param ex The exception that we're logging
     */
    private static void log(Level level, Throwable ex, String fmt, Object... args) {
        final String format =  (fmt != null) ? fmt : "(null)";
        log(level, () -> new Event(format(fmt, args), new Date(), level, ex));
    }

    /**
     * Log an event to the correct logger based on the package of the executing
     * code.
     *
     * @param level The log level
     * @param entry The event to log
     */
    private static void log(Level level, Supplier<Event> entry) {
        PackageLogger pl = Router.getInstance().route();
        if (level.code() <= pl.getLevel().code()) {
            try {
                pl.getLogger().log(entry.get());
            } catch (Throwable ex) {
                StackTraceElement el = findErrorLine(ex);
                pl.getLogger().log(new Event(format("Error while logging at %s:%d: '%s'", el.getFileName(), el.getLineNumber(), ex.getMessage()), new Date(), ERROR, ex));
            }
        }
    }

    /**
     * Find the stack trace element for the line on which a log call went wrong.
     *
     * @param ex The exception raised
     * @return The element for the line it broke
     */
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
