package joni.retry.task;

import java.util.concurrent.atomic.AtomicInteger;

import joni.retry.Retriable;

/**
 * @author Jonatan Ivanov
 */
public class SuccessfulTask implements Task {
    private final AtomicInteger COUNT = new AtomicInteger();

    @Override
    @Retriable
    public void execute() {
        COUNT.incrementAndGet();
    }

    @Override
    public int getExecutionCount() {
        return COUNT.get();
    }
}
