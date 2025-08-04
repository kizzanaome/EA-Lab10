package bank.scheduler;

import bank.dao.AccountDAO;
import bank.domain.Account;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class BankStatementPrinter {
    private final AccountDAO accountDAO;

    public BankStatementPrinter(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Scheduled(fixedRate = 10000) // runs every 10 seconds
    public void printStatements() {
        System.out.println("===== Bank Statement =====");
        for (Account acc : accountDAO.getAccounts()) {
            System.out.println("Account: " + acc.getAccountnumber() +
                    " | Balance: " + acc.getBalance());
        }
        System.out.println("==========================");
    }
}
