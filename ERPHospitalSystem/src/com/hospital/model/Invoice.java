package com.hospital.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private String id;
    private double cost;
    private String patientId;
    private String visitId;
    private String insurance;
    private String createdAt;
    private String finalizedAt;

    public Invoice(String id, double cost, String patientId, String visitId, String insurance) {
        this.id = id;
        this.cost = cost;
        this.patientId = patientId;
        this.visitId = visitId;
        this.insurance = insurance;
        this.createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.finalizedAt = null;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getFinalizedAt() {
        return finalizedAt;
    }

    public boolean isFinalized() {
        return finalizedAt != null;
    }

    public void setFinalized() {
        this.finalizedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ;
    }
}
