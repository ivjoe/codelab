package joni.retry.task;

import java.util.concurrent.atomic.AtomicInteger;

import joni.retry.Retriable;

/**
 * @author Jonatan Ivanov
 */
public class OddExecutionsFailedTask implements Task {
    private final AtomicInteger COUNT = new AtomicInteger();

    @Override
    @Retriable
    public void execute() {
        System.out.println("exec");
        COUNT.incrementAndGet();
        if (COUNT.get() % 2 != 0) {
            throw new RuntimeException("simulated error");
        }
    }

    @Override
    public int getExecutionCount() {
        return COUNT.get();
    }
}
