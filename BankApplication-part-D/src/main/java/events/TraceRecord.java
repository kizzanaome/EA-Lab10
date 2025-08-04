package events;

import java.time.LocalDateTime;

public class TraceRecord {
    private LocalDateTime timestamp;
    private long accountNumber;
    private String operation;
    private double amount;

    public TraceRecord(LocalDateTime timestamp, long accountNumber, String operation, double amount) {
        this.timestamp = timestamp;
        this.accountNumber = accountNumber;
        this.operation = operation;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TraceRecord{" +
                "timestamp=" + timestamp +
                ", accountNumber=" + accountNumber +
                ", operation='" + operation + '\'' +
                ", amount=" + amount +
                '}';
    }
}

