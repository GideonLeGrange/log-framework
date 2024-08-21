package me.legrange.log;

import me.legrange.log.logger.AnimatedConsoleLogger;
import me.legrange.log.logger.ConsoleLogger;

import java.util.Date;

import static java.lang.String.format;
import static me.legrange.log.Log.animate;
import static me.legrange.log.Log.info;

public class test {

    public static void main(String[] args) throws Exception {
        Log.setDefaultLogger(new AnimatedConsoleLogger());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                        info("YOLO");
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
        try (Animation an = animate()) {
            for (int i = 100000000; i  >0; i = i / 5) {
                an.update(format("Updating %d of %d [%.2f]", i, 10, ((double)i)/10*100));
                Thread.sleep(1000);
//                try (Animation an2 = animate()) {
//                    an.update("Some other thing happening with a counter " + i);
//                }

                Thread.sleep(1000);
                info("Did number %d", i);
            }
        }
    }
}
