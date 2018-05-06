package me.legrange.log.test;

import static me.legrange.log.Log.debug;
import static me.legrange.log.Log.info;
import static me.legrange.log.Log.warning;

/**
 *
 * @author gideon
 */
public class TestLog {
    
    public static void main(String...args) {
        info("Info %d", 1);
        info("Info %d %d", 2);
        debug("Debug %d", 1);
        warning("Waring %s", "alpha");
    }
    
}
