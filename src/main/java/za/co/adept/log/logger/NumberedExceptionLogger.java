package za.co.adept.log.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.util.UUID;
import za.co.adept.log.Event;
import za.co.adept.log.Logger;

/**
 * A logger implementation that numbers exceptions and logs them with the number
 * to another logger, while writing the stack trace to a file.
 *
 * @author gideon
 */
public class NumberedExceptionLogger implements Logger {

    private final Logger logger;
    private final PrintWriter out;

    public NumberedExceptionLogger(Logger logger, String exceptionFile) throws LoggerException {
        this.logger = logger;
        out = openFile(exceptionFile);
    }

    @Override
    public synchronized void log(Event entry) {
        if (entry.getThrowable().isPresent()) {
            String id = getId();
            entry = new Event(entry.getMessage() + format(" [%s]", id), entry.getTimestamp(), entry.getLevel(), entry.getThrowable().get());
            writeToFile(id, entry);
        }
        logger.log(entry);
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private void writeToFile(String id, Event entry) {
        out.printf("[#%s - %s]\n", id, entry.getTimestamp());
        entry.getThrowable().get().printStackTrace(out);
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
