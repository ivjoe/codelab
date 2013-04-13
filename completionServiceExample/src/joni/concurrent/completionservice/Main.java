package joni.concurrent.completionservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jonatan Ivanov
 */
public class Main {
    private static final int NUMBER_OF_TASKS = 10;
    private static final int CORE_THREADPOOL_SIZE = 3;
    private static final int MAX_THREADPOOL_SIZE = 3;
    private static final long KEEP_ALIVE_TIME = 2;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    private static final int WORK_QUEUE_CAPACITY = NUMBER_OF_TASKS;

    /**
     * @throws ExecutionException
     * @throws InterruptedException
     * @param args
     * @throws Exception
     * @throws
     */
    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(WORK_QUEUE_CAPACITY);
        ExecutorService executorService = new ThreadPoolExecutor(CORE_THREADPOOL_SIZE, MAX_THREADPOOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, workQueue);

        List<Task> tasks = getTasks();
        work(executorService, tasks);
        executorService.shutdownNow();
    }

    private static List<Task> getTasks() {
        List<Task> tasks = new ArrayList<Task>();
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            tasks.add(new Task());
        }
        return tasks;
    }

    private static <V, T extends Callable<V>> void work(Executor executor, Collection<T> tasks) {
        CompletionService<V> service = new ExecutorCompletionService<V>(executor);

        for (T task : tasks) {
            service.submit(task);
        }

        for (int i = 0; i < tasks.size(); i++) {
            try {
                V result = service.take().get();
                System.out.println(String.valueOf(result));
            }
            catch (InterruptedException ie) {
                System.out.println("Thread is interrupted: " + ie.getMessage());
            }
            catch (ExecutionException ee) {
                System.out.println("Error during execution: " + ee.getMessage());
            }
        }
    }
}
