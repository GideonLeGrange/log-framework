package me.legrange.test.test1;

import static me.legrange.log.Level.DEBUG;
import me.legrange.log.Log;
import static me.legrange.log.Log.critical;
import static me.legrange.log.Log.debug;
import static me.legrange.log.Log.error;
import static me.legrange.log.Log.info;
import static me.legrange.log.Log.warning;

/**
 *
 * @author gideon
 */
public class test1 {

    public test1() {
      Log.setLevel(DEBUG);
    }

    public void t1() {
        debug("t2: debug");
        info("t2: info ");
        warning("t2: warning");
        error("t2: error ");
        critical("t2: critical");
    }

    static {
        Log.setLogger(new ShoutyLogger());

    }
}
