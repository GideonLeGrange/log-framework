package me.legrange.log;

import java.util.Date;
import java.util.Optional;

/**
 * A log entry
 *
 * @author gideon1
 */
public final class Event {

    private final String message;
    private final Date timestamp;
    private final Level level;
    private final Optional<Throwable> throwable;

    public Event(String message, Date timestamp, Level level, Throwable throwable) {
        this.message = message;
        this.timestamp = timestamp;
        this.level = level;
        this.throwable = Optional.ofNullable(throwable);
    }

    public Event(String message, Date timestamp, Level level) {
        this(message, timestamp, level, null);
    }

    /**
     * Get the logged message
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the timestamp of the logged event
     *
     * @return The timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Get the log level at which this event is logged
     *
     * @return The level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Get the exception associated with this event if there is one.
     *
     * @return An optional exception
     */
    public Optional<Throwable> getThrowable() {
        return throwable;
    }
}
