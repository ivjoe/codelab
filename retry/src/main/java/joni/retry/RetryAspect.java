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
        final RetryHandler retryHandler = retriable.value();
        for (int i = 0; i < retryHandler.getRetryCount() + 1; i++) {
            try {
                if (i > 0) {
                    System.out.println("Retrying, " + i + ". attempt caused by: " + error);
                }

                return joinPoint.proceed();
            }
            catch (final Throwable e) {
                error = e;
                if (!retryHandler.shouldRetry(e)) {
                    break;
                }
            }
        }
        throw error;
    }
}
