package com.hospital.model;

public class IncidentReport {

    private String id;
    private String patientId;
    private String reporterId;
    private String description;
    private String timestamp;

    public IncidentReport() {}

    public IncidentReport(String id, String patientId, String reporterId,
                          String description, String timestamp) {
        this.id = id;
        this.patientId = patientId;
        this.reporterId = reporterId;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}




