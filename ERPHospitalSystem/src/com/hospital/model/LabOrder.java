package com.hospital.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LabOrder {
    private String orderId;
    private String patientId;
    private String testName;
    private String orderingDoctorId;
    private String status;
    private String results;
    private String technicianNotes;
    private String dateOrdered;
    private String dateCompleted;
    private boolean isCritical;

    public LabOrder(String orderId, String patientId, String testName, String orderingDoctorId) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.testName = testName;
        this.orderingDoctorId = orderingDoctorId;
        this.status = "Pending Lab Collection";
        this.results = null;
        this.technicianNotes = null;
        this.dateOrdered = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.dateCompleted = null;
        this.isCritical = false;
    }

    public LabOrder() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getOrderingDoctorId() {
        return orderingDoctorId;
    }

    public void setOrderingDoctorId(String orderingDoctorId) {
        this.orderingDoctorId = orderingDoctorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results, String notes) {
        this.results = results;
        this.technicianNotes = notes;
    }

    public String getTechnicianNotes() {
        return technicianNotes;
    }

    public void setTechnicianNotes(String technicianNotes) {
        this.technicianNotes = technicianNotes;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public boolean isFinalized() {
        return "Finalized".equals(status);
    }

    public void markCritical() {
        this.isCritical = true;
    }

    @Override
    public String toString() {
        return "LabOrder{" +
                "orderId='" + orderId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", testName='" + testName + '\'' +
                ", orderingDoctorId='" + orderingDoctorId + '\'' +
                ", status='" + status + '\'' +
                ", isCritical=" + isCritical +
                ", dateOrdered='" + dateOrdered + '\'' +
                '}';
    }
}
