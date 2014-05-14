package joni.tracelog;

import joni.tracelog.task.Task;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jonatan Ivanov
 */
public class TracelogDemo {
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void test() throws Exception {
        final Task task = context.getBean("task", Task.class);

        task.doSomethingVoid();
        task.getInt(42);
        task.getString("a");
        task.throwException();
    }
}
