package com.oussamadahir.banking.model;

/** Convenience factory that returns a fresh AccountBuilder. */
public class BankDirector {
    private BankDirector() {}

    public static BankAccount.AccountBuilder accountBuilder() {
        return new BankAccount.AccountBuilder();
    }
}
