package me.legrange.log;

import static java.lang.String.format;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import me.legrange.log.logger.ConsoleLogger;

/**
 * This class works out where to route log requests.
 *
 * @author gideon
 */
final class Router extends SecurityManager {

    private static final Router INSTANCE = new Router();
    private PackageLogger DEFAULT = new PackageLogger("", new ConsoleLogger(), Level.INFO);
    private Map<String, PackageLogger> packageLoggers = new HashMap<>();

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
    synchronized void setDefaultLogger(Logger def) {
        if (!DEFAULT.getPack().equals("")) {
            throw new IllegalStateException(format("The default logger is already set. It was called from %s. Either your application or that library is calling setDefaultLogger() incorrectly", DEFAULT.getPack()));
        }
        List<String> toRemove = new LinkedList();
        packageLoggers.entrySet().stream().filter((ent) -> (ent.getValue() == DEFAULT)).forEachOrdered((ent) -> {
            toRemove.add(ent.getKey());
        });
        toRemove.forEach(pack -> packageLoggers.remove(pack));
        DEFAULT = new PackageLogger(calledFromPackage(), def, Level.INFO);
    }

    /**
     * Set the default (last resort) logging level on which to filter message.
     */
    void setDefaultLevel(Level level) {
        DEFAULT.setLevel(level);
    }

    /**
     * Set the logger for the package calling the method.
     *
     * @param logger The logger to use.
     */
    synchronized void setLogger(Logger logger) {
        setLogger(calledFromPackage(), logger);
    }

    /**
     * Set the logger for given name (should be a package but can be any name really)
     *
     * @param logger The logger to use.
     */
    synchronized void setLogger(String name, Logger logger) {
        PackageLogger forName = determineLoggerFor(name);
        setLogger(name, new PackageLogger(name, logger, forName.getLevel()));
    }

    /**
     * Set the log level for the package calling the method.
     *
     * @param level The required log level.
     */
    void setLevel(Level level) {
        setLevel(calledFromPackage(), level);
    }

    /**
     * Set the log level for the given name.
     *
     * @name name The name for which to set the level.
     * @param level The required log level.
     */
    void setLevel(String name, Level level) {
        PackageLogger forPkg = determineLoggerFor(name);
        if (!forPkg.getPack().equals(name)) {
            setLogger(name, new PackageLogger(name, forPkg.getLogger(), level));
        } else {
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
        String name = "";
        while (idx < contex.length) {
            Package pkg = contex[idx].getPackage();
            if (pkg != null) {
                name = pkg.getName();
                if (!name.equals(Log.class.getPackage().getName())) {
                    return name;
                }
            } else {  // it was null, so we're no longer in the logger package but we have no package name
                return "";
            }
            idx++;
        }
        return name;
    }

}
