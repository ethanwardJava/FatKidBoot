package com.arcade.FatKidBoot.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class ControllerExceptionLoggingAspect {
    // Pointcut: all methods inside GlobalExceptionHandler
    @Pointcut("within(com.arcade.FatKidBoot.exception.GlobalExceptionHandler.*)")
    public void exceptionHandlerMethods() {
    }

    // Log when any exception handler catches something
    @AfterThrowing(pointcut = "exceptionHandlerMethods()", throwing = "ex")
    public void logHandledExceptions(JoinPoint joinPoint, Exception ex) {

        String method = joinPoint.getSignature().getName();

        log.warn("""
                        ---> Exception handled by GlobalExceptionHandler
                        Method     : {}
                        Exception  : {}
                        Message    : {}
                        """,
                method,
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }
}