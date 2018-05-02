package me.legrange.log.test;

import java.util.Date;

/**
 *
 * @author gideon
 */
public class TestDateSpeed {
    
    
    public static void main(String...args) {
        int N = 200000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; ++i) {
            new Date();
        }
        long stop = System.currentTimeMillis();
        double u = ((double)(stop - start))*1000/N;
        System.out.printf("%f us per new Date()\n", u);
    }
    
}
