package me.legrange.log;

/**
 * Container class that controls logging for a package.
 *
 * @author gideon
 */
final class PackageLogger {

    private final String pack;
    private final Logger logger;
    private Level level;

    PackageLogger(String pack, Logger logger, Level level) {
        this.pack = pack;
        this.logger = logger;
        this.level = level;
    }

    String getPack() {
        return pack;
    }

    Logger getLogger() {
        return logger;
    }

    Level getLevel() {
        return level;
    }

    void setLevel(Level level) {
        this.level = level;
    }

}
