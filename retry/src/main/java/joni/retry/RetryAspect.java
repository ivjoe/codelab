package joni.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Jonatan Ivanov
 */
@Aspect
public class RetryAspect {
    @Around("@annotation(retriable)")
    public Object retry(final ProceedingJoinPoint joinPoint, final Retriable retriable) throws Throwable {
        Throwable error = null;
        final RetryPolicy retryPolicy = retriable.value();
        for (int i = 0; i < retryPolicy.getRetryCount() + 1; i++) {
            try {
                if (i > 0) {
                    final String msg = String.format("Retrying %s.%s, %d. attempt caused by: %s",
                            joinPoint.getTarget().getClass().getSimpleName(),
                            joinPoint.getSignature().getName(),
                            i,
                            error.getClass().getSimpleName());
                    System.out.println(msg);
                    sleep(retryPolicy.getDelay());
                }

                return joinPoint.proceed();
            }
            catch (final Throwable e) {
                error = e;
                if (!retryPolicy.shouldRetry(e)) {
                    break;
                }
            }
        }
        throw error;
    }

    private void sleep(final long millis) {
        try {
            if (millis > 0) {
                Thread.sleep(millis);
            }
        }
        catch (final InterruptedException e) {
            System.out.println(e);
        }
    }
}
