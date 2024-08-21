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

    public FileLogger(String fileName) throws LoggerException {
        out = openFile(fileName);
        this.fileName = fileName;
    }

    @Override
    public void log(Event entry) {
        out.printf(Standard.format(entry));
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
