package za.co.adept.log;

import static java.lang.String.format;
import za.co.adept.log.logger.SystemOutLogger;
import java.util.HashMap;
import java.util.Map;

/**
 * This class works out where to route log requests.
 *
 * @author gideon
 */
final class Router extends SecurityManager {

    private static final Router INSTANCE = new Router();
    private PackageLogger DEFAULT = new PackageLogger("", new SystemOutLogger(), Level.INFO);
    private final Map<String, PackageLogger> packageLoggers = new HashMap<>();

    /**
     * Get the singleton router instance.
     *
     * @return The router instance for this VM.
     */
    static Router getInstance() {
        return INSTANCE;
    }

    /**
     * Find the logger to which to route a log event based on the calling code.
     *
     * @return The logger to use
     */
    PackageLogger route() {
        String pack = calledFromPackage();
        String find = pack;
        PackageLogger logger = packageLoggers.get(find);
        if (logger == null) {
            logger = determineLoggerFor(pack);
            packageLoggers.put(pack, logger);
        }
        return logger;
    }

    /**
     * Set the default (last resort) logger to use.
     *
     * @param def The logger
     */
    void setDefaultLogger(Logger def) {
        String from = calledFromPackage();
        if (!DEFAULT.getPack().equals("")) {
            throw new IllegalStateException(format("The default logger is already set. It was called from %s. Either your application or that library is calling setDefaultLogger() incorrectly", DEFAULT.getPack()));
        }
        DEFAULT = new PackageLogger(from, def, Level.INFO);
    }
    
    /** 
     * Set the default (last resort) logging level on which to filter message. 
     */
    void setDefaultLevel(Level level) {
        DEFAULT.setLevel(level);
    }
    
    /** Set the logger for the package calling the method. 
     * 
     * @param logger The logger to use.
     */
    void setLogger(Logger logger) {
        String pkg = calledFromPackage();
        PackageLogger forPkg = determineLoggerFor(pkg);
        setLogger(pkg, new PackageLogger(pkg, logger, forPkg.getLevel()));        
    }

    /** Set the log level for the package calling the method. 
     * 
     * @param level The required log level. 
     */
    void setLevel(Level level) {
        String pkg = calledFromPackage();
        PackageLogger forPkg = determineLoggerFor(pkg);
        if (!forPkg.getPack().equals(pkg)) { 
            setLogger(pkg, new PackageLogger(pkg, forPkg.getLogger(), level));
        }
        else {
            forPkg.setLevel(level);
        }
    }
    
    private void setLogger(String pkg, PackageLogger pkgLogger) {
        packageLoggers.put(pkg, pkgLogger);
    }
    
    // Private constructor since we only want a singleton. 
    private Router() {
    }
   
    /**
     * Recursively find a logger by shortening the package name until we find
     * one.
     *
     * @param find The package name
     * @return The logger
     */
    private PackageLogger determineLoggerFor(String find) {
        PackageLogger logger = packageLoggers.get(find);
        if (logger == null) {
            if (find.indexOf('.') > 0) {
                return determineLoggerFor(find.substring(0, find.lastIndexOf('.')));
            }
            return DEFAULT;
        }
        return logger;
    }

    /**
     * work out from what user package the logger is being called.
     *
     * @return The package name
     */
    private String calledFromPackage() {
        Class[] contex = getClassContext();
        int idx = 0;
        String pack = "";
        while (idx < contex.length) {
            pack = contex[idx].getPackage().getName();
            if (!pack.equals(Log.class.getPackage().getName())) {
                break;
            }
            idx++;
        }
        return pack;
    }

}
