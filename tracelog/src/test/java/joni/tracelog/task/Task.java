package joni.tracelog.task;

/**
 * @author Jonatan Ivanov
 */
public interface Task {

    void doSomethingVoid();

    int getInt(final int param);

    String getString(final String param);

    void throwException() throws IndexOutOfBoundsException;
}
