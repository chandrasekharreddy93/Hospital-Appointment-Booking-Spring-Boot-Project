package com.stackly.hospital.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppointmentLoggingAspect {

    // Apply logging around all AppointmentService methods.
    @Around("execution(* com.example.hospital.service.AppointmentService.*(..))")
    public Object logAppointmentOperations(
            ProceedingJoinPoint joinPoint) throws Throwable {

        // Get the method name.
        String methodName = joinPoint.getSignature().getName();

        // Store the starting time.
        long startTime = System.currentTimeMillis();

        System.out.println("\n[AOP] Starting method: " + methodName);

        try {

            // Execute the actual service method.
            Object result = joinPoint.proceed();

            System.out.println("[AOP] Method completed: " + methodName);

            return result;

        } catch (Throwable exception) {

            System.out.println("[AOP] Method failed: " + methodName);

            throw exception;

        } finally {

            // Calculate total execution time.
            long endTime = System.currentTimeMillis();

            System.out.println(
                    "[AOP] Execution time: "
                    + (endTime - startTime) + " ms"
            );
        }
    }
}

