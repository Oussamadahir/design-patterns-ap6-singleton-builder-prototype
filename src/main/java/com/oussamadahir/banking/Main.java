package com.oussamadahir.banking;

import com.oussamadahir.banking.model.*;
import com.oussamadahir.banking.prototype.AccountPrototypeRegistry;
import com.oussamadahir.banking.repository.AccountRepositoryImpl;
import com.oussamadahir.banking.util.JsonSerializer;

public class Main {
    public static void main(String[] args) {
        // ---- SINGLETON: one shared repository for the whole app ----
        AccountRepositoryImpl repo = AccountRepositoryImpl.getInstance();
        JsonSerializer<BankAccount> serializer = new JsonSerializer<>();

        // ---- BUILDER: build a couple of "from scratch" accounts step by step ----
        BankAccount fromBuilder1 = BankDirector.accountBuilder()
                .balance(15000.0)
                .currency("MAD")
                .type(AccountType.CURRENT_ACCOUNT)
                .status(AccountStatus.ACTIVATED)
                .customer(new Customer(101L, "Anissa Tazi"))
                .build();
        repo.save(fromBuilder1);

        BankAccount fromBuilder2 = BankDirector.accountBuilder()
                .balance(2500.0)
                .currency("EUR")
                .type(AccountType.SAVING_ACCOUNT)
                .status(AccountStatus.CREATED)
                .customer(new Customer(102L, "Karim El Idrissi"))
                .build();
        repo.save(fromBuilder2);

        // ---- PROTOTYPE: register three templates, then clone-and-tweak ----
        AccountPrototypeRegistry registry = new AccountPrototypeRegistry();

        registry.register("vip-current-mad", BankDirector.accountBuilder()
                .balance(100000.0)
                .currency("MAD")
                .type(AccountType.CURRENT_ACCOUNT)
                .status(AccountStatus.ACTIVATED)
                .build());

        registry.register("student-saving-mad", BankDirector.accountBuilder()
                .balance(500.0)
                .currency("MAD")
                .type(AccountType.SAVING_ACCOUNT)
                .status(AccountStatus.ACTIVATED)
                .build());

        registry.register("business-current-usd", BankDirector.accountBuilder()
                .balance(75000.0)
                .currency("USD")
                .type(AccountType.CURRENT_ACCOUNT)
                .status(AccountStatus.ACTIVATED)
                .build());

        // Create 5 new accounts by cloning templates and adjusting only customer + balance.
        Object[][] newCustomers = {
                {"vip-current-mad",      201L, "Salma Bennani",  120000.0},
                {"vip-current-mad",      202L, "Younes Alami",   180000.0},
                {"student-saving-mad",   203L, "Hicham Naciri",      350.0},
                {"student-saving-mad",   204L, "Imane Rahmouni",     600.0},
                {"business-current-usd", 205L, "Atlas Logistics",  92000.0},
        };

        for (Object[] row : newCustomers) {
            String key = (String) row[0];
            Long custId = (Long) row[1];
            String name = (String) row[2];
            double balance = (Double) row[3];

            BankAccount cloned = registry.obtain(key);
            cloned.setCustomer(new Customer(custId, name));
            cloned.setBalance(balance);
            repo.save(cloned);
        }

        // ---- Result ----
        System.out.println("\n=== " + repo.count() + " accounts in repository ===");
        repo.findAll().forEach(a -> System.out.println(serializer.toJson(a)));
    }
}
