package com.oussamadahir.banking.model;

/**
 * BankAccount supports two creation patterns:
 *   1) Builder (via AccountBuilder / BankDirector) for full step-by-step construction.
 *   2) Prototype (Cloneable) for fast creation by cloning a pre-configured template.
 */
public class BankAccount implements Cloneable {
    private Long accountId;
    private double balance;
    private String currency;
    private AccountType type;
    private AccountStatus status;
    private Customer customer;

    public BankAccount() {}

    public BankAccount(Long accountId, double balance, String currency,
                       AccountType type, AccountStatus status) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
        this.type = type;
        this.status = status;
    }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    /** Prototype: deep clone (Customer is also copied). */
    @Override
    public BankAccount clone() {
        try {
            BankAccount copy = (BankAccount) super.clone();
            if (this.customer != null) {
                copy.customer = new Customer(this.customer.getId(), this.customer.getName());
            }
            // freshly cloned accounts start without an id — repository assigns one on save.
            copy.accountId = null;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return "BankAccount{accountId=" + accountId +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", customer=" + (customer == null ? "null" : customer.getName()) +
                '}';
    }

    /** Builder. */
    public static class AccountBuilder {
        private final BankAccount bankAccount = new BankAccount();

        public AccountBuilder accountId(Long id)         { bankAccount.accountId = id; return this; }
        public AccountBuilder currency(String currency)  { bankAccount.currency = currency; return this; }
        public AccountBuilder balance(double balance)    { bankAccount.balance = balance; return this; }
        public AccountBuilder type(AccountType type)     { bankAccount.type = type; return this; }
        public AccountBuilder status(AccountStatus s)    { bankAccount.status = s; return this; }
        public AccountBuilder customer(Customer c)       { bankAccount.customer = c; return this; }
        public BankAccount build()                       { return this.bankAccount; }
    }
}
