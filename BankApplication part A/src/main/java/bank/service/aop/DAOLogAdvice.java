package bank.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DAOLogAdvice {

    @Before("execution(* bank.dao.*.*(..))")
    public void logDAOCall(JoinPoint joinPoint) {
        System.out.println("DAO call: " + joinPoint.getSignature().getName());
    }
}

