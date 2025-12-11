package com.hospital.model;

import java.time.LocalDateTime;

public class MedicationOrder {

    private String medicationId;
    private int quantity;
 //   private LocalDateTime orderDate;
    private int orderId;
    private static int orderIdCounter = 0;
    private String status; // e.g., "PENDING", "FULFILLED"
    public MedicationOrder() {
    }

    public MedicationOrder(String medicationId, int quantity) {
        this.medicationId = medicationId;
        this.quantity = quantity;
  //      this.orderDate = LocalDateTime.now();
        this.orderId = ++orderIdCounter;
        this.status = "PENDING";
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
   /* public LocalDateTime getOrderDate() {
        return orderDate;
    }*/
    public int getOrderId() {
        return orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
