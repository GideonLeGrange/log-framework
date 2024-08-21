package me.legrange.log;

public interface AnimatedLogger extends Logger {

    Animation start();

    void update(Animation animation, String message);

    void end(Animation animation);
}
