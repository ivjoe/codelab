package joni.concurrent.completionservice;

import java.util.concurrent.Callable;

/**
 * @author Jonatan Ivanov
 */
public class Task implements Callable<Long> {

    private static final long MAX_SLEEP = 500;
    private static final int MAX_RESULT_VALUE = 100;

    @Override
    public Long call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " Task starts.");
        Thread.sleep(Math.round(Math.random() * MAX_SLEEP));
        Long result = Long.valueOf(Math.round(Math.random() * MAX_RESULT_VALUE));
        System.out.println(Thread.currentThread().getName() + " Task stops.");

        return result;
    }
}
