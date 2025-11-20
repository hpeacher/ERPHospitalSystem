package com.hospital.model;

public class VisitRecord {
    private String id;
    private String patientId;
    private String vitals;
    private String notes;
    private DischargeChecklist dischargeChecklist;
    private String invoiceId;
    private Diagnosis diagnosis;

    
    private String followUpRecommendation;

    public VisitRecord(String patientId) {
        this.id = "V" + ++visitIdCounter;
        this.patientId = patientId;
        this.vitals = null;
        this.notes = null;
        this.dischargeChecklist = null;
        this.diagnosis = null;
    public VisitRecord() {
        this.vitals = "";
        this.notes = "";
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

    public String getId() {
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
