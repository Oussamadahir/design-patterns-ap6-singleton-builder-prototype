package com.oussamadahir.banking.repository;

import com.oussamadahir.banking.model.BankAccount;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Eager Singleton: instance created at class load. Thread-safe by JVM guarantees. */
public class AccountRepositoryImpl implements AccountRepository {
    private static final AccountRepositoryImpl INSTANCE;

    static {
        System.out.println("[Singleton] AccountRepositoryImpl instantiation");
        INSTANCE = new AccountRepositoryImpl();
    }

    private AccountRepositoryImpl() {}

    private final Map<Long, BankAccount> bankAccountMap = new HashMap<>();
    private long accountsCount = 0;

    public static AccountRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized BankAccount save(BankAccount bankAccount) {
        Long id = ++accountsCount;
        bankAccount.setAccountId(id);
        bankAccountMap.put(id, bankAccount);
        return bankAccount;
    }

    @Override
    public List<BankAccount> findAll() {
        return new ArrayList<>(bankAccountMap.values());
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return Optional.ofNullable(bankAccountMap.get(id));
    }

    @Override
    public List<BankAccount> searchAccount(Predicate<BankAccount> predicate) {
        return bankAccountMap.values().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public synchronized BankAccount update(BankAccount account) {
        bankAccountMap.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public synchronized void deleteById(Long id) {
        bankAccountMap.remove(id);
    }

    public int count() {
        return bankAccountMap.size();
    }
}
