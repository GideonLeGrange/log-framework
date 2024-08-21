package me.legrange.log;

/**
 * A logging level
 *
 * @author gideon
 */
public enum Level {


    CRITICAL(0, "💥"),
    ERROR(1, "⚡️"),
    WARNING(2, "⚠️"),
    INFO(3, "ℹ️"),
    DEBUG(4, "🔍");

    private final int code;
    private final String emoji;

    public int code() {
        return code;
    }

    public String emoji() {
        return emoji;
    }

    Level(int code, String emoji) {
        this.code = code;
        this.emoji = emoji;
    }

}
