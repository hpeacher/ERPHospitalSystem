package com.hospital.model;

public class VisitRecord {
    private int id;
    private String patientId;
    private String vitals;
    private String notes;
    private DischargeChecklist dischargeChecklist;
    private Invoice invoice;
    private Diagnosis diagnosis;

    private String followUpRecommendation;

    public VisitRecord() {
        this.vitals = "";
        this.notes = "";
    }

    public VisitRecord(String patientId) {
        this.vitals = "";
        this.notes = "";
        this.patientId = patientId;
    }

    public String getFollowUpRecommendation() {
        return followUpRecommendation;
    }

    public void setFollowUpRecommendation(String recommendation) {
        this.followUpRecommendation = recommendation;
    }

    public VisitRecord(String patientId, int nextIdForThisPatient) {
        this.patientId = patientId;
        this.id = nextIdForThisPatient;
        this.vitals = "";
        this.notes = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
