package com.oussamadahir.banking.prototype;

import com.oussamadahir.banking.model.BankAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype registry: stores pre-configured BankAccount templates by a string key.
 * Calling {@link #obtain(String)} returns a clone of the template (Prototype pattern).
 */
public class AccountPrototypeRegistry {
    private final Map<String, BankAccount> prototypes = new HashMap<>();

    public void register(String key, BankAccount prototype) {
        prototypes.put(key, prototype);
    }

    /** Returns a fresh clone of the named prototype, or null if the key is unknown. */
    public BankAccount obtain(String key) {
        BankAccount template = prototypes.get(key);
        if (template == null) return null;
        return template.clone();
    }

    public java.util.Set<String> keys() {
        return prototypes.keySet();
    }
}
