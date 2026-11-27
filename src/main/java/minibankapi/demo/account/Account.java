package minibankapi.demo.account;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import minibankapi.demo.Exception.InsufficientFundsException;
import minibankapi.demo.entity.Customer;
import minibankapi.demo.bussinesslogic.CustomerService;
import minibankapi.demo.Exception.ResourceNotFoundException;


@Entity
@Table(name = "accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = new BigDecimal("0.00");

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Account() { }

    public Account(String accountNumber, Customer customer, AccountType type) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.type = type;
    }

    @PrePersist
    void onCreate() { this.createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public AccountType getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}


