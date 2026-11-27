package minibankapi.demo.account;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import minibankapi.demo.Exception.InsufficientFundsException;
import minibankapi.demo.Exception.ResourceNotFoundException;
import minibankapi.demo.bussinesslogic.CustomerService;
import minibankapi.demo.entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {


    private final AccountRepository accounts;
    private final AccountTransactionRepository transactions;
    private final CustomerService customers;

    public AccountService(AccountRepository accounts,
                          AccountTransactionRepository transactions,
                          CustomerService customers) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.customers = customers;
    }

    @Transactional
    public AccountResponse open(OpenAccountRequest req) {
        Customer customer = customers.find(req.customerId());
        Account account = new Account(generateAccountNumber(), customer, req.type());
        return AccountResponse.from(accounts.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse get(Long id) {
        return AccountResponse.from(find(id));
    }

    @Transactional
    public AccountResponse deposit(Long id, BigDecimal amount) {
        Account account = find(id);
        account.setBalance(account.getBalance().add(amount));
        record(account, TransactionType.DEPOSIT, amount);
        return AccountResponse.from(account);
    }

    @Transactional
    public AccountResponse withdraw(Long id, BigDecimal amount) {
        Account account = find(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds: balance " + account.getBalance() + ", requested " + amount);
        }
        account.setBalance(account.getBalance().subtract(amount));
        record(account, TransactionType.WITHDRAWAL, amount);
        return AccountResponse.from(account);
    }

    private void record(Account account, TransactionType type, BigDecimal amount) {
        transactions.save(new AccountTransaction(account, type, amount, account.getBalance()));
    }

    public Account find(Long id) {
        return accounts.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account " + id + " not found"));
    }

    private String generateAccountNumber() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
    }




}
