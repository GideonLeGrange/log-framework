package me.legrange.log;

import me.legrange.log.logger.console.AnimatedConsoleLogger;

public class test {
    public static void main(String[] args) {
        Log.setDefaultLogger(new AnimatedConsoleLogger());
        try (Animation an = Log.animate()) {
            for (int i = 0; i < 100; ++i) {
                an.updateProgress(i, 100, "Working on " + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
