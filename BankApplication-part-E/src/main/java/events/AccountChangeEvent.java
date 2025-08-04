package events;


import org.springframework.context.ApplicationEvent;

public class AccountChangeEvent extends ApplicationEvent {

    private final long accountNumber;
    private final String operation;
    private final double amount;

    public AccountChangeEvent(Object source, long accountNumber, String operation, double amount) {
        super(source);
        this.accountNumber = accountNumber;
        this.operation = operation;
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getOperation() {
        return operation;
    }

    public double getAmount() {
        return amount;
    }
}

