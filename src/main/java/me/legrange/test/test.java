package me.legrange.test;

import static me.legrange.log.Level.DEBUG;
import static me.legrange.log.Level.ERROR;
import static me.legrange.log.Level.INFO;
import static me.legrange.log.Level.WARNING;
import me.legrange.log.Log;
import static me.legrange.log.Log.error;
import static me.legrange.log.Log.info;
import static me.legrange.log.Log.critical;
import static me.legrange.log.Log.warning;
import static me.legrange.log.Log.debug;
import me.legrange.test.test1.test1;

/**
 *
 * @author gideon
 */
public class test {

    public static void main(String... args) {
        test t = new test();
        t.t1();
        t.t3();
    }

    private test() {
        Log.setLevel(WARNING);
    }

    private void t1() {
        debug("t1: debug %s", "Hello");
        info("t1: info ");
        warning("t1: warning");
        error("t1: error ");
        critical("t1: critical");
        
        for (int i = 0; i < 1000; ++i) {
            // some stuff 
            debug(() ->  makeDebugMessage());
        }
        
    }
    
    private String makeDebugMessage() {
        return "info";
    }

    
    private void t3() {
        test1 t = new test1();
        t.t1();
    }

}
