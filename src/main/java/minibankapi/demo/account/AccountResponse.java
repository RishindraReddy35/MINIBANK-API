package minibankapi.demo.account;

import java.math.BigDecimal;


public record AccountResponse(Long id, String accountNumber, Long customerId,
                              AccountType type, BigDecimal balance)

{
    public static AccountResponse from(Account a) {
        return new AccountResponse(a.getId(), a.getAccountNumber(),
                a.getCustomer().getId(), a.getType(), a.getBalance());
    }
}
