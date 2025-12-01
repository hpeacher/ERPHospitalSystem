package com.hospital.repository.impl;

import java.io.File;
import com.hospital.repository.JsonSerializer;
import com.hospital.model.Transaction;

public class TransactionRepository {
    private final File transactionDir;

    public TransactionRepository(String basePath) {
        this.transactionDir = new File(basePath, "transactions");
        if (!transactionDir.exists()) {
            transactionDir.mkdirs();
        }
    }

    public void save(Transaction tx) {
        File file = new File(transactionDir, tx.getTransactionId() + ".json");
        JsonSerializer.writeToFile(file, tx);
    }

    public Transaction findById(String transactionId) {
        File file = new File(transactionDir, transactionId + ".json");
        if (!file.exists())
            return null;
        return JsonSerializer.readFromFile(file, Transaction.class);
    }
}
