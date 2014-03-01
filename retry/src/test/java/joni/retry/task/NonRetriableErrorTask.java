package joni.retry.task;

import java.util.concurrent.atomic.AtomicInteger;

import joni.retry.Retriable;
import joni.retry.RetryHandler;

/**
 * @author Jonatan Ivanov
 */
public class NonRetriableErrorTask implements Task {
    private final AtomicInteger COUNT = new AtomicInteger();

    @Override
    @Retriable(RetryHandler.HTTP)
    public void execute() {
        COUNT.incrementAndGet();
        throw new RuntimeException("simulated error");
    }

    @Override
    public int getExecutionCount() {
        return COUNT.get();
    }
}
