package joni.tracelog.task;

/**
 * @author Jonatan Ivanov
 */
public interface Task {

    void doSomethingVoid() throws InterruptedException;

    int getInt(final int param) throws InterruptedException;

    String getString(final String param) throws InterruptedException;

    void throwException() throws IndexOutOfBoundsException, InterruptedException;
}
