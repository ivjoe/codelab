package joni.retry;

import joni.retry.task.Task;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jonatan Ivanov
 */
public class RetryTest {
    private static ClassPathXmlApplicationContext context;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        context = new ClassPathXmlApplicationContext("appContext.xml");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (context != null) {
            context.close();
        }
    }

    @Test
    public void successfulTaskTest() {
        final Task task = context.getBean("successfulTask", Task.class);
        task.execute();
        Assert.assertEquals(1, task.getExecutionCount());
    }

    @Test(expected = RuntimeException.class)
    public void failedTaskTest() {
        final Task task = context.getBean("failedTask", Task.class);
        try {
            task.execute();
        }
        finally {
            Assert.assertEquals(4, task.getExecutionCount());
        }
    }

    @Test(expected = RuntimeException.class)
    public void nonRetriableErrorTaskTest() {
        final Task task = context.getBean("nonRetriableErrorTask", Task.class);
        try {
            task.execute();
        }
        finally {
            Assert.assertEquals(1, task.getExecutionCount());
        }
    }
}
