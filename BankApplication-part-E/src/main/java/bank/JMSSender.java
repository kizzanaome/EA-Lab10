package bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class JMSSender {
    @Autowired
    private JmsTemplate  jmsTemplate;

    public  void  sendJMSMessage(String message){
        System.out.println("BnakApp sending JMS message " + message);
        jmsTemplate.convertAndSend("taxQueue", message);
    }
}
