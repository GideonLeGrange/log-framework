package me.legrange.log;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * A log entry
 *
 * @author gideon1
 */
public final class Event {

    private final UUID uuid = UUID.randomUUID();
    private final String message;
    private final Date timestamp;
    private final Level level;
    private final Throwable throwable;

    public Event(String message, Date timestamp, Level level, Throwable throwable) {
        this.message = message;
        this.timestamp = timestamp;
        this.level = level;
        this.throwable = throwable;
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
        return Optional.ofNullable(throwable);
    }

    /** Get the UUID for this event
     *
     * @return The UUID
     */
    public UUID getUuid() {
        return uuid;
    }
}
