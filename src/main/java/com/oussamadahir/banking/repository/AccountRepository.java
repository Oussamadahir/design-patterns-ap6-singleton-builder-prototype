package com.oussamadahir.banking.repository;

import com.oussamadahir.banking.model.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface AccountRepository {
    BankAccount save(BankAccount account);
    List<BankAccount> findAll();
    Optional<BankAccount> findById(Long id);
    List<BankAccount> searchAccount(Predicate<BankAccount> predicate);
    BankAccount update(BankAccount account);
    void deleteById(Long id);
}
