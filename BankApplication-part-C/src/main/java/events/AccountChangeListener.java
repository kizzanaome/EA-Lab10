package events;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountChangeListener {

    private final List<TraceRecord> traceRecords = new ArrayList<>();

    @EventListener
    public void handleAccountChange(AccountChangeEvent event) {
        // 1. Simulate sending an email
        System.out.println("EMAIL: Change detected in account " + event.getAccountNumber()
                + " | Operation: " + event.getOperation()
                + " | Amount: " + event.getAmount());

        // 2. Save a trace record
        TraceRecord record = new TraceRecord(LocalDateTime.now(), event.getAccountNumber(),
                event.getOperation(), event.getAmount());
        traceRecords.add(record);

        System.out.println("Trace record saved: " + record);
    }

    public List<TraceRecord> getTraceRecords() {
        return traceRecords;
    }
}
