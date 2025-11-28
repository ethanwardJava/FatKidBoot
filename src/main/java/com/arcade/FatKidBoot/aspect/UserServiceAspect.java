package com.arcade.FatKidBoot.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Component
@Aspect
@Log4j2
public class UserServiceAspect {

    @Pointcut("within(com.arcade.FatKidBoot.service..*)")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logService(ProceedingJoinPoint pjp) throws Throwable {

        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        StopWatch sw = new StopWatch();
        sw.start();

        try {
            Object result = pjp.proceed();

            sw.stop();
            log.info("[SERVICE OK] {}.{} | args={} | return={} | {} ms",
                    className,
                    methodName,
                    Arrays.toString(args),
                    (result != null ? result.getClass().getSimpleName() : "void"),
                    sw.getTotalTimeMillis()
            );

            return result;

        } catch (Throwable ex) {
            sw.stop();
            log.error("[SERVICE ERROR] {}.{} | args={} | {} ms | exception={}",
                    className,
                    methodName,
                    Arrays.toString(args),
                    sw.getTotalTimeMillis(),
                    ex.toString()
            );
            throw ex;
        }
    }
}
