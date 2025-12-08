package com.hospital.model;

public class MedicationStock {
    private String medicationId;
    private String name;
    private int quantityAvailable;
    private int reorderThreshold;

    public MedicationStock() {
    }

    public MedicationStock(String medicationId, String name, int quantityAvailable, int reorderThreshold) {
        this.medicationId = medicationId;
        this.name = name;
        this.quantityAvailable = quantityAvailable;
        this.reorderThreshold = reorderThreshold;
    }

    // Getters and Setters
    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    @Override
    public String toString() {
        return "MedicationStock{" +
                "medicationId='" + medicationId + '\'' +
                ", name='" + name + '\'' +
                ", quantityAvailable=" + quantityAvailable +
                ", reorderThreshold=" + reorderThreshold +
                '}';
    }
}
