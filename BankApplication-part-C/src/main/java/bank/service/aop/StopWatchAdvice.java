package bank.service.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class StopWatchAdvice {

    @Around("execution(* bank.service.*.*(..))")
    public Object methodTimer(ProceedingJoinPoint call) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start(call.getSignature().getName());
        Object retVal = call.proceed();
        sw.stop();
        System.out.println("Time for method " + call.getSignature().getName() + ": " + sw.getLastTaskTimeMillis() + " ms");
        return retVal;
    }
}
