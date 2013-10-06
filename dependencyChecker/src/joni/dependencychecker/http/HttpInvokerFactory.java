package joni.dependencychecker.http;

/**
 * @author Jonatan Ivanov
 */
public class HttpInvokerFactory {
    private static final HttpInvoker INSTANCE = new HttpInvoker();

    private HttpInvokerFactory() {
    }

    public static HttpInvoker getInstance() {
        return INSTANCE;
    }
}
