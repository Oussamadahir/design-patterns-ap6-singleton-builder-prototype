# Activité 6 — Patterns Singleton, Builder, Prototype

**Design Patterns** — Master M2 SDIA II-BDCC 2025/2026
**Auteur :** Oussama Dahir

## Patterns implémentés

### 1. Singleton — `AccountRepositoryImpl`
Garantit une **instance unique** du repository en mémoire, accessible globalement. Implémentation thread-safe via initialisation statique (eager singleton).

### 2. Builder — `BankAccount.AccountBuilder`
Construit un `BankAccount` immuable étape par étape (`accountId().balance().currency().type().status().build()`) — évite les constructeurs à 5+ paramètres positionnels.

### 3. Prototype — `BankAccount` (Cloneable) + `AccountPrototypeRegistry`
Crée des nouveaux comptes par **clonage** d'un prototype pré-configuré, sans repasser par toute la construction. Le `AccountPrototypeRegistry` stocke des templates nommés (`"vip-current-mad"`, `"basic-saving-usd"`, ...) qu'on clone à la demande.

## Structure

```
src/main/java/com/oussamadahir/banking/
├── Main.java                       # Démo combinée des 3 patterns
├── model/
│   ├── BankAccount.java            # Cloneable + nested AccountBuilder
│   ├── AccountStatus.java
│   ├── AccountType.java
│   ├── BankDirector.java           # Accès au Builder
│   └── Customer.java
├── repository/
│   ├── AccountRepository.java      # Interface CRUD
│   └── AccountRepositoryImpl.java  # SINGLETON eager + thread-safe
├── prototype/
│   └── AccountPrototypeRegistry.java   # PROTOTYPE registry
└── util/
    └── JsonSerializer.java          # Sérialisation via Jackson
```

## Exécution

```bash
mvn compile exec:java -Dexec.mainClass="com.oussamadahir.banking.Main"
```

La démo :
1. Récupère l'instance unique du repository (Singleton).
2. Enregistre des prototypes (`vip-current-mad`, `student-saving-mad`, `business-current-usd`) dans la registry.
3. Pour 5 nouveaux clients, clone un prototype, ajuste customer + balance, et persiste — beaucoup plus court que de re-builder à chaque fois.
4. Sérialise les comptes en JSON.
