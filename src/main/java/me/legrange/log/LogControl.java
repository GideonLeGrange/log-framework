package me.legrange.log;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * The component used to control logging on an application-wide level
 *
 * @author gideon
 */
public class LogControl {

    public LogControl() throws FileNotFoundException {
        if (System.out instanceof LogOutputStream) {
            throw new IllegalStateException("Log control has already been installed");
        }
        System.setOut(new LogOutputStream());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // close files and shit 
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // do some logging stuff yo
                
            }
        });
    }
   

    /**
     * Set the logger for the package calling the logger.
     *
     * @param logger
     */
    public void setLogger(Logger logger) {
        Router.getInstance().setLogger(logger);
    }

    /**
     * Set the default application logger. Only call this from your
     * application's main code. This must not be called by libraries.
     *
     * @param logger
     */
    public void setDefaultLogger(Logger logger) {
        Router.getInstance().setDefaultLogger(logger);
    }

    /**
     * Set the default logging level. Only call this from your application's
     * main code. This must not be called by libraries.
     *
     * @param level The default level to set.
     */
    public void setDefaultLevel(Level level) {
        Router.getInstance().setDefaultLevel(level);
    }

    private class LogOutputStream extends PrintStream {

        public LogOutputStream() throws FileNotFoundException {
            super("foo");
        }

    }

}
