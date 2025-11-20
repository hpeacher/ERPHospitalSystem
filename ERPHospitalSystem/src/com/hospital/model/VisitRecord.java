package com.hospital.model;

public class VisitRecord {
    private String id;
    private String patientId;
    private String vitals;
    private String notes;
    private DischargeChecklist dischargeChecklist;
    private String invoiceId;
    private Diagnosis diagnosis;

    private static int visitIdCounter = 0;

    public VisitRecord(String patientId) {
        this.id = "V" + ++visitIdCounter;
        this.patientId = patientId;
        this.vitals = null;
        this.notes = null;
        this.dischargeChecklist = null;
        this.diagnosis = null;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getVitals() {
        return vitals;
    }

    public void setVitals(String vitals) {
        this.vitals = vitals;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DischargeChecklist getDischargeChecklist() {
        return dischargeChecklist;
    }

    public void setDischargeChecklist(DischargeChecklist dischargeChecklist) {
        this.dischargeChecklist = dischargeChecklist;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
