package bank.service.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JMSLogAdvice {

    @After("execution(* bank.jms.JMSSender.sendJMSMessage(..)) && args(message)")
    public void logJMSMessage(JoinPoint joinPoint, String message) {
        System.out.println("JMS message logged: " + message);
    }
}
