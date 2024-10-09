package ru.kvshe.languagetrainer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "log WordService")
public class WordAspect {
    @Pointcut("execution(* ru.kvshe.languagetrainer.service.WordService.*(..))")
    public void wordServicePointcut() {
    }

    @Before(value = "wordServicePointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("Method {} is started", joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "wordServicePointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("Method {} thrown {}, description: {}",
                joinPoint.getSignature().getName(),
                ex.getClass().getName(),
                ex.getMessage());
    }

    @Around(value = "wordServicePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        log.info("Method {} executed in {} ms",
                joinPoint.getSignature().getName(),
                duration);

        return result;
    }
}
