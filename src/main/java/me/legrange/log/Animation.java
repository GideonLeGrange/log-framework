package me.legrange.log;

public interface Animation extends AutoCloseable {


    void update(String message);

    void updateProgress(int done, int total, String message);

    @Override
    void close();
}
