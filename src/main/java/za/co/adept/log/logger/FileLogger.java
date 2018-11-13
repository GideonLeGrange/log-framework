package za.co.adept.log.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import za.co.adept.log.Event;
import za.co.adept.log.Logger;

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
