package joni.retry;

import java.io.IOException;

/**
 * @author Jonatan Ivanov
 */
public enum RetryHandler {
    DEFAULT,

    HTTP {
        @Override
        public boolean shouldRetry(final Throwable error) {
            return error instanceof IOException;
        }
    };

    public int getRetryCount() {
        return 3;
    }

    public boolean shouldRetry(final Throwable error) {
        return true;
    }
}
