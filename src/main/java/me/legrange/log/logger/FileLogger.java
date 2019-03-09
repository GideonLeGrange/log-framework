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
public class FileLogger implements Logger {

    private final PrintWriter out;

    public FileLogger(String fileName) throws LoggerException {
        out = openFile(fileName);
    }

    @Override
    public void log(Event entry) {
        out.printf("%s [%s]: %s\n", entry.getTimestamp(), entry.getLevel(), entry.getMessage());
        out.flush();
    }

    protected final PrintWriter openFile(String fileName) throws LoggerException {
        try {
            return new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException ex) {
            throw new LoggerException(format("Error opening log file '%s': %s", fileName, ex.getMessage()), ex);
        }

    }

}
