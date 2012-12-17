package joni.jmx;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jonatan Ivanov
 */
public class Main {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("context.xml");
        Thread.sleep(Long.MAX_VALUE);
    }
}
