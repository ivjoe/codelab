package joni.retry;

import java.io.IOException;

/**
 * RetryPolicy controls the retry mechanism, see the methods of the class.
 * 
 * @author Jonatan Ivanov
 */
public enum RetryPolicy {
    DEFAULT,

    HTTP {
        @Override
        public long getDelay() {
            return 500;
        }

        @Override
        public boolean shouldRetry(final Throwable error) {
            return error instanceof IOException;
        }
    };

    /**
     * @return No. of retry attempts.
     */
    public int getRetryCount() {
        return 3;
    }

    /**
     * @return Delay in ms between two retry attempts.
     */
    public long getDelay() {
        return 0;
    }

    /**
     * @param error
     * @return Should or shouldn't retry on this error.
     */
    public boolean shouldRetry(final Throwable error) {
        return true;
    }
}
