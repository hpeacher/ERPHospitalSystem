package com.hospital.model;

public class VisitRecord {
    private int id;
    private String patientId;
    private String vitals;
    private String notes;
    private DischargeChecklist dischargeChecklist;
    private Invoice invoice;
    private Diagnosis diagnosis;
    private String date;

    private static int visitIdCounter = 0;

    public VisitRecord(String patientId, String date) {
        this.id = ++visitIdCounter;
        this.patientId = patientId;
        this.vitals = null;
        this.notes = null;
        this.dischargeChecklist = null;
        this.invoice = null;
        this.diagnosis = null;
        this.date = date;
    }

    public int getId() {
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
