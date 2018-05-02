package me.legrange.log;

import java.util.function.Supplier;
import static me.legrange.log.Level.CRITICAL;
import static me.legrange.log.Level.DEBUG;
import static me.legrange.log.Level.ERROR;
import static java.lang.String.format;
import java.util.Date;
import static me.legrange.log.Level.INFO;
import static me.legrange.log.Level.WARNING;

/**
 *
 * @author gideon
 */
public class Log {
    

    public static void critical(String fmt, Object... args) {
        log(CRITICAL, fmt, args);
    }

    public static void error(String fmt, Object... args) {
        log(ERROR, fmt, args);
    }

    public static void warning(String fmt, Object... args) {
        log(WARNING, fmt, args);
    }

    public static void info(String fmt, Object... args) {
        log(INFO, fmt, args);
    }

    public static void debug(String fmt, Object... args) {
        log(DEBUG, fmt, args);
    }
    

    private static void log(Level level, String fmt, Object... args) {
        log(level, () -> format(fmt, args));
    }
    
    private static void log(Level level, Supplier<String> entry) {
        if (level.code() <= INFO.code()) {
            String text;
            try {
                 text = entry.get();
            }
            catch (Throwable ex) {
                StackTraceElement el = findErrorLine(ex);
                sendToLog(ERROR, format("Error while logging at %s:%d. '%s'", el.getFileName(), el.getLineNumber(), ex.getMessage()));
;                return;
            }
            sendToLog(level, text);
 
            
        }
    }
    
    private static void sendToLog(Level level, String text) {
        System.out.printf("%s [%s] %s\n", new Date(), level, text);
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
            }
            else {
                if (foundMe) { 
                    return el;
                }
            }
            
            i++;
        }
        return null;
    }

}
