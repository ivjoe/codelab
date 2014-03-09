package joni.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retriable annotation makes methods retriable.
 * 
 * @author Jonatan Ivanov
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retriable {
    /**
     * @return {@link RetryPolicy}
     */
    RetryPolicy value() default RetryPolicy.DEFAULT;
}
