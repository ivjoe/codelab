package joni.retry.task;

/**
 * @author Jonatan Ivanov
 */
public interface Task {

    void execute();

    int getExecutionCount();
}
