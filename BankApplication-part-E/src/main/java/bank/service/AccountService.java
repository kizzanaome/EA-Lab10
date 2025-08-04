package bank.service;

import bank.dao.IAccountDAO;
import bank.domain.Account;
import bank.domain.Customer;
import bank.jms.IJMSSender;
import events.AccountChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountService implements IAccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private final IAccountDAO accountDAO;
	private final ICurrencyConverter currencyConverter;
	private final IJMSSender jmsSender;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public AccountService(IAccountDAO accountDAO,
						  ICurrencyConverter currencyConverter,
						  IJMSSender jmsSender,
						  ApplicationEventPublisher publisher) {
		this.accountDAO = accountDAO;
		this.currencyConverter = currencyConverter;
		this.jmsSender = jmsSender;
		this.publisher = publisher;
	}

	public Account createAccount(long accountNumber, String customerName) {
		Account account = new Account(accountNumber);
		Customer customer = new Customer(customerName);
		account.setCustomer(customer);
		accountDAO.saveAccount(account);
		logger.info("Account created: accountNumber={} , customerName={}", accountNumber, customerName);
		return account;
	}

	public void deposit(long accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.deposit(amount);
		accountDAO.updateAccount(account);

		publisher.publishEvent(new AccountChangeEvent(this, accountNumber, "Deposit", amount));
		logger.info("Deposited {} into account {}", amount, accountNumber);

		if (amount > 10000) {
			String alert = "Large deposit detected: " + amount +
					" EUR on account " + accountNumber;
			jmsSender.sendJMSMessage(alert);
			logger.warn("Large deposit alert sent for account {}", accountNumber);
		}
	}

	public Account getAccount(long accountNumber) {
		return accountDAO.loadAccount(accountNumber);
	}

	public Collection<Account> getAllAccounts() {
		return accountDAO.getAccounts();
	}

	public void withdraw(long accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.withdraw(amount);
		accountDAO.updateAccount(account);
		publisher.publishEvent(new AccountChangeEvent(this, accountNumber, "Withdraw", amount));
		logger.info("Withdrew {} from account {}", amount, accountNumber);
	}

	public void depositEuros(long accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		double amountDollars = currencyConverter.euroToDollars(amount);
		account.deposit(amountDollars);
		accountDAO.updateAccount(account);
		logger.info("Deposited {} euros ({} dollars) into account {}", amount, amountDollars, accountNumber);

		if (amountDollars > 10000) {
			jmsSender.sendJMSMessage("Deposit of $ " + amountDollars + " to account with accountNumber= " + accountNumber);
			logger.warn("Large deposit alert sent for account {}", accountNumber);
		}
	}

	public void withdrawEuros(long accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		double amountDollars = currencyConverter.euroToDollars(amount);
		account.withdraw(amountDollars);
		accountDAO.updateAccount(account);
		logger.info("Withdrew {} euros ({} dollars) from account {}", amount, amountDollars, accountNumber);
	}

	public void transferFunds(long fromAccountNumber, long toAccountNumber, double amount, String description) {
		Account fromAccount = accountDAO.loadAccount(fromAccountNumber);
		Account toAccount = accountDAO.loadAccount(toAccountNumber);
		fromAccount.transferFunds(toAccount, amount, description);
		accountDAO.updateAccount(fromAccount);
		accountDAO.updateAccount(toAccount);

		publisher.publishEvent(new AccountChangeEvent(this, fromAccountNumber, "Transfer", amount));
		logger.info("Transferred {} from account {} to account {}. Description: {}", amount, fromAccountNumber, toAccountNumber, description);

		if (amount > 10000) {
			jmsSender.sendJMSMessage("TransferFunds of $ " + amount + " from account with accountNumber= " + fromAccountNumber +
					" to account with accountNumber= " + toAccountNumber);
			logger.warn("Large transfer alert sent for accounts {} -> {}", fromAccountNumber, toAccountNumber);
		}
	}
}
