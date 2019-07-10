package com.lampart.actuator.performancemonitor;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MainAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // final long start = System.currentTimeMillis();
        long startTime = System.nanoTime();

        final Object proceed = joinPoint.proceed();

        // final long executionTime = System.currentTimeMillis() - start;

        long timeTaken = System.nanoTime() - startTime;

        // System.out.println(joinPoint.getSignature() + " executed in " + executionTime
        // + "ms");

        System.out
                .println(joinPoint.getSignature() + " executed in " + TimeUnit.NANOSECONDS.toMillis(timeTaken) + "ms");

        return proceed;
    }

}
