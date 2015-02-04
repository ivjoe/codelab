package joni.tracelog.task;

import joni.tracelog.EnableTracelog;

/**
 * @author Jonatan Ivanov
 */
public class SimpleTask implements Task {

    @EnableTracelog
    @Override
    public void doSomethingVoid() throws InterruptedException {
        Thread.sleep(100);
    }

    @EnableTracelog
    @Override
    public int getInt(final int param) throws InterruptedException {
        Thread.sleep(100);
        return param;
    }

    @EnableTracelog
    @Override
    public String getString(final String param) throws InterruptedException {
        Thread.sleep(100);
        return param;
    }

    @EnableTracelog
    @Override
    public void throwException() throws IndexOutOfBoundsException, InterruptedException {
        Thread.sleep(100);
        throw new IndexOutOfBoundsException("Simulated exception");
    }
}
