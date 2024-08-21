package me.legrange.log.logger.console;

import me.legrange.log.AnimatedLogger;
import me.legrange.log.Animation;
import me.legrange.log.Event;

import java.util.Stack;

/**
 * A basic logger implementation that prints to System.out, with exceptions going to System.err
 *
 * @author gideon
 */
public final class AnimatedConsoleLogger implements AnimatedLogger {

    private final Stack<ConsoleAnimation> animations = new Stack<>();

    @Override
    public synchronized void log(Event entry) {
        if (!animations.empty()) {
            clear();
        }
        System.out.printf("%s [%s]: %s\n", entry.getTimestamp(), entry.getLevel(), entry.getMessage());
        if (entry.getThrowable().isPresent()) {
            entry.getThrowable().get().printStackTrace(System.err);
        }
        if (!animations.empty()) {
            update(animations.peek(), animations.peek().getMessage());
        }
    }

    @Override
    public Animation start() {
        ConsoleAnimation animation = new ConsoleAnimation(this);
        animations.push(animation);
        return animation;
    }

    @Override
    public void update(Animation animation, String message) {
        clear();
        System.out.printf(message);
    }

    @Override
    public synchronized void end(Animation animation) {
        clear();
        System.out.print("");
        animations.pop();
    }

    private void clear() {
        if (!animations.empty()) {
            System.out.print("\r");
        }
    }

}
