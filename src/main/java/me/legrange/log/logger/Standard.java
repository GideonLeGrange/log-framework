package me.legrange.log.logger;

import me.legrange.log.Event;

final class Standard {

    static String format(Event entry, boolean useEmoji) {
        return String.format("%s %s %s\n",
                entry.getTimestamp(),
                useEmoji ? entry.getLevel().emoji() : "[" + entry.getLevel().name() + "]",
                entry.getMessage());
    }

    private Standard() {
    }
}
