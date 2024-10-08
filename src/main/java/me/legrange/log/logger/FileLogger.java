package me.legrange.log.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import me.legrange.log.Event;
import me.legrange.log.Logger;

/**
 *
 * @author gideon
 */
public final class FileLogger implements Logger {

    private final PrintWriter out;
    private final String fileName;
    private final boolean useEmoji;

    public FileLogger(String fileName, boolean useEmoji) throws LoggerException {
        out = openFile(fileName);
        this.fileName = fileName;
        this.useEmoji = useEmoji;
    }

    public FileLogger(String fileName) throws LoggerException {
        this(fileName, false);
    }

    @Override
    public void log(Event entry) {
        out.printf(Standard.format(entry, useEmoji));
        out.flush();
    }

    @Override
    public String getName() {
        return format("%s[%s]", getClass().getSimpleName(), fileName);
    }

    private PrintWriter openFile(String fileName) throws LoggerException {
        try {
            return new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException ex) {
            throw new LoggerException(format("Error opening log file '%s': %s", fileName, ex.getMessage()), ex);
        }

    }

}
