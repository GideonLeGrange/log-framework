package me.legrange.log;

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

    /** Get the singleton router instance. 
     * 
     * @return The router instance for this VM.
     */
    static Router getInstance() { 
        return INSTANCE;
    }

    /** Find the logger to which to route a log event based on the calling code. 
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
    
    /** Set the default (last resort) logger to use. 
     * 
     * @param def The logger
     */
    void setDefaultLogger(Logger def) {
        this.DEFAULT = new PackageLogger("", def, Level.INFO);
    }
    
    // Private constructor since we only want a singleton. 
    private Router() {
    }

    /** Recurively find a logger by shortening the package name until we find one. 
     * 
     * @param find The package name
     * @return The logger
     */
    private PackageLogger determineLoggerFor(String find) {
        PackageLogger logger = null;
        if ((logger == null) && (find.indexOf('.') > 0)) {
            return determineLoggerFor(find.substring(0, find.lastIndexOf('.')));
        }
        return DEFAULT;
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
