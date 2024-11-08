package ru.kvshe.languagetrainer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Аспект для логирования методов класса WordService.
 * Позволяет отслеживать начало выполнения, время выполнения и исключения в методах.
 */
@Aspect
@Component
@Slf4j(topic = "log WordService")
public class WordAspect {
    @Pointcut("execution(* ru.kvshe.languagetrainer.service.WordService.*(..))")
    public void wordServicePointcut() {
    }

    /**
     * Метод, выполняемый перед каждым методом в WordService.
     * Логирует начало выполнения метода.
     *
     * @param joinPoint содержит информацию о методе, который вызывается.
     */
    @Before(value = "wordServicePointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("Method {} is started", joinPoint.getSignature().getName());
    }

    /**
     * Метод, выполняемый при возникновении исключения в методах WordService.
     * Логирует название метода, тип исключения и описание ошибки.
     *
     * @param joinPoint содержит информацию о методе, в котором возникло исключение.
     * @param ex        исключение, которое было выброшено.
     */
    @AfterThrowing(value = "wordServicePointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("Method {} thrown {}, description: {}",
                joinPoint.getSignature().getName(),
                ex.getClass().getName(),
                ex.getMessage());
    }

    /**
     * Метод, выполняемый вокруг каждого метода в WordService.
     * Измеряет и логирует время выполнения метода.
     *
     * @param joinPoint содержит информацию о методе, который вызывается.
     * @return результат выполнения метода.
     * @throws Throwable если в методе возникает исключение.
     */
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
