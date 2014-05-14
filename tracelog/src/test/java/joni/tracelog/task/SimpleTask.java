package joni.tracelog.task;

import joni.tracelog.EnableTracelog;

/**
 * @author Jonatan Ivanov
 */
public class SimpleTask implements Task {

    @EnableTracelog
    @Override
    public void doSomethingVoid() {
    }

    @EnableTracelog
    @Override
    public int getInt(final int param) {
        return param;
    }

    @EnableTracelog
    @Override
    public String getString(final String param) {
        return param;
    }

    @EnableTracelog
    @Override
    public void throwException() throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException("Simulated exception");
    }
}
