package me.legrange.log.logger;

import me.legrange.log.Event;

final class Standard {

    static String format(Event entry) {
        return String.format("%s [%s]: %s\n", entry.getTimestamp(), entry.getLevel(), entry.getMessage());
    }

    private Standard() {
    }
}
