package com.hospital.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private String type; // e.g., "WITHDRAWAL", "RESTOCK"
    private String medicationId;
    private int quantity;
 //   private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(String type, String medicationId, int quantity) {
        this.transactionId = UUID.randomUUID().toString();
        this.type = type;
        this.medicationId = medicationId;
        this.quantity = quantity;
   //     this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

/*    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }*/

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", type='" + type + '\'' +
                ", medicationId='" + medicationId + '\'' +
                ", quantity=" + quantity +
   //             ", timestamp=" + timestamp +
                '}';
    }
}