package joni.tracelog;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Jonatan Ivanov
 */

@Aspect
public class TracelogAspect {

    @AfterReturning(pointcut = "@annotation(tracelog)", returning = "result")
    public void logAfterReturning(final JoinPoint joinPoint, final EnableTracelog tracelog, final Object result) {
        final StringBuilder sb = new StringBuilder();
        appendMethodAndResult(sb, joinPoint, result);
        log(sb);
    }

    @AfterThrowing(pointcut = "@annotation(tracelog)", throwing = "error")
    public void logAfterThrowing(final JoinPoint joinPoint, final EnableTracelog tracelog, final Throwable error) {
        final StringBuilder sb = new StringBuilder();
        appendMethodAndError(sb, joinPoint, error);
        log(sb);
    }

    private void appendMethodAndResult(final StringBuilder sb, final JoinPoint joinPoint, final Object result) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        appendSignature(sb, signature);
        sb.append(" ");
        appendArguments(sb, joinPoint.getArgs());
        sb.append(" ");
        appendResult(sb, signature, result);
    }

    private void appendMethodAndError(final StringBuilder sb, final JoinPoint joinPoint, final Throwable error) {
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

    private void log(final StringBuilder sb) {
        System.out.println(sb.toString());
    }
}
