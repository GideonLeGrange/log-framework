package me.legrange.log.logger.console;

import me.legrange.log.Animation;

import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;

final class ConsoleAnimation implements Animation {
    private static final String[] BLOCKS = new String[] { "", "▏","▎","▍","▌","▋","▊","▉","█"};
    private static final int BAR_LENGTH = 20;

    private final AnimatedConsoleLogger animatedConsoleLogger;
    private String message;

    ConsoleAnimation(AnimatedConsoleLogger animatedConsoleLogger) {
        this.animatedConsoleLogger = animatedConsoleLogger;
    }

    public void update(String message) {
        this.message = message;
        animatedConsoleLogger.update(this, message);
    }

    @Override
    public void updateProgress(int done, int total, String message) {
        var buf= new StringBuilder();
        buf.append("\r");
        buf.append("[");
            buf.append(generateProgressBar(done,0, total, 20 ));
        buf.append("]");
        buf.append(" ");
        buf.append(message);
        System.out.print(buf.toString());
        System.out.flush();
    }

    @Override
    public void close() {
        animatedConsoleLogger.end(this);
    }

    public String getMessage() {
        return message;
    }

    private static final String[] blocks = new String[]{"", "▏", "▎", "▍", "▌", "▋", "▊", "▉", "█"};
    private static final double base = 0.125;

    public static String generateProgressBar(double value, double vmin, double vmax, int length) {
        // normalize value
        value = min(max(value, vmin), vmax);
        value = (value - vmin) / (vmax - vmin);

        double v = value * length;
        int x = (int) floor(v); // integer part
        double y = v - x;       // fractional part
        // round(base*math.floor(float(y)/base),prec)/base
        int i = (int) ((round((base * floor(y / base)) * 1000D) / base) / 1000D);
        String bar = "█".repeat(x) + blocks[i];
        int n = length - bar.length();
        return bar + " ".repeat(n);
    }
}
