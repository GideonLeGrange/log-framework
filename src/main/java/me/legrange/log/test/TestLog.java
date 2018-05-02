package me.legrange.log.test;

import static me.legrange.log.Log.info;

/**
 *
 * @author gideon
 */
public class TestLog {
    
    public static void main(String...args) {
        info("Info %d", 1);
        info("Info %d %d", 2);
    }
    
}
