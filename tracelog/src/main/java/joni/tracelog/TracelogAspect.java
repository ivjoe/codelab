package joni.tracelog;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Jonatan Ivanov
 */

@Aspect
public class TracelogAspect {

    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    @Around("@annotation(tracelog)")
    public Object logAround(final ProceedingJoinPoint joinPoint, final EnableTracelog tracelog) throws Throwable {
        final StringBuilder sb = new StringBuilder();
        long startTime = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            appendMethodAndDuration(sb, joinPoint, result, startTime);
            return result;
        }
        catch (Throwable error) {
            appendMethodAndDuration(sb, joinPoint, error, startTime);
            throw error;
        }
        finally {
            log(sb);
        }
    }

    private long calculateDuration(final long startTime) {
        return TIME_UNIT.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
    }

    // Use System.nanoTime() to provide the startTime
    private void appendMethodAndDuration(final StringBuilder sb, final JoinPoint joinPoint, final Object result, final long startTime) {
        appendMethod(sb, joinPoint, result);
        sb.append(" ");
        appendDuration(sb, calculateDuration(startTime), TIME_UNIT);
    }

    private void appendMethod(final StringBuilder sb, final JoinPoint joinPoint, final Object result) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        appendSignature(sb, signature);
        sb.append(" ");
        appendArguments(sb, joinPoint.getArgs());
        sb.append(" ");
        appendResult(sb, signature, result);
    }

    // Use System.nanoTime() to provide the startTime
    private void appendMethodAndDuration(final StringBuilder sb, final JoinPoint joinPoint, final Throwable error, final long startTime) {
        appendMethod(sb, joinPoint, error);
        sb.append(" ");
        appendDuration(sb, calculateDuration(startTime), TIME_UNIT);
    }

    private void appendMethod(final StringBuilder sb, final JoinPoint joinPoint, final Throwable error) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        appendSignature(sb, signature);
        sb.append(" ");
        appendArguments(sb, joinPoint.getArgs());
        sb.append(" ");
        appendError(sb, signature, error);
    }

    private void appendSignature(final StringBuilder sb, final Signature signature) {
        sb.append("Method: ").append(signature.toString());
    }

    private void appendArguments(final StringBuilder sb, final Object[] args) {
        sb.append("Arguments: ").append(Arrays.toString(args));
    }

    private void appendResult(final StringBuilder sb, final MethodSignature signature, final Object result) {
        if (signature.getReturnType() != void.class) {
            sb.append("Result: ").append(result);
        }
    }

    private void appendError(final StringBuilder sb, final MethodSignature signature, final Throwable error) {
        sb.append("Error: ").append(error);
    }

    private void appendDuration(final StringBuilder sb, final long duration, final TimeUnit unit) {
        appendDuration(sb, duration);
        sb.append(getSymbol(unit));
    }

    private void appendDuration(final StringBuilder sb, final long duration) {
        sb.append("Duration: ").append(duration);
    }

    private String getSymbol(TimeUnit unit) {
        if (unit != null) {
            switch (unit) {
                case NANOSECONDS:   return "ns";
                case MICROSECONDS:  return "μs";
                case MILLISECONDS:  return "ms";
                case SECONDS:       return "s";
                case MINUTES:       return "m";
                case HOURS:         return "h";
                case DAYS:          return "d";
                default:            return "";
            }
        }
        else {
            return "";
        }
    }

    private void log(final StringBuilder sb) {
        System.out.println(sb.toString());
    }
}
