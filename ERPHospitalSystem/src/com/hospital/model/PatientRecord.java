package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

public class PatientRecord {
    private String patientId;
    private PatientAdministrativeInfo administrativeInfo;
    private PatientMedicalInfo medicalInfo;
    private ArrayList<VisitRecord> visits;
    private String insurance;

    // Constructor
    public PatientRecord(String patientId, PatientAdministrativeInfo administrativeInfo,
            PatientMedicalInfo medicalInfo, String insurance) {
        this.patientId = patientId;
        this.administrativeInfo = administrativeInfo;
        this.medicalInfo = medicalInfo;
        this.visits = new ArrayList<VisitRecord>();
        this.insurance = insurance;
    }

    public PatientRecord() {
        this.visits = new ArrayList<VisitRecord>();
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public PatientAdministrativeInfo getAdministrativeInfo() {
        return administrativeInfo;
    }

    public PatientMedicalInfo getMedicalInfo() {
        return medicalInfo;
    }

    public ArrayList<VisitRecord> getVisits() {
        return visits;
    }

    public String getInsurance() {
        return insurance;
    }

    // Setters
    public void setAdministrativeInfo(PatientAdministrativeInfo administrativeInfo) {
        this.administrativeInfo = administrativeInfo;
    }

    public void setMedicalInfo(PatientMedicalInfo medicalInfo) {
        this.medicalInfo = medicalInfo;
    }

    public String getMedicalHistory() {
        return "Retrieving medical history for: " + administrativeInfo.getName();
    }

    public void addVisit(VisitRecord visit) {
        this.visits.add(visit);
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
    public List<VisitRecord> getVisitHistory() {
        return this.visits;
    }
    public void setVisits(ArrayList<VisitRecord> visits) {
        this.visits = visits;
    }

    public PatientAdministrativeInfo getAdminInfo() {
        return this.administrativeInfo;
    }
    public void setAdminInfo(PatientAdministrativeInfo adminInfo) {
        this.administrativeInfo = adminInfo;
    }
    public PatientMedicalInfo getMedInfo() {
        return this.medicalInfo;
    }
    public void setMedInfo(PatientMedicalInfo medInfo) {
        this.medicalInfo = medInfo;
    }

    public void setVisitHistory(ArrayList<VisitRecord> visitHistory) {
        this.visits = visitHistory;
    }

    public ArrayList<VisitRecord> getVisitRecords() {
        return this.visits;
    }

    public VisitRecord getVisitRecord(int index) {
        return this.visits.get(index - 1);
    }

    public void deleteVisitRecord(){
        this.visits = new ArrayList<>();
    }

    public void printVisitHistory() {
        if (visits.isEmpty()) {
            System.out.println("No visits.");
            return;
        }

        for (int i = 0; i < visits.size(); i++) {
            System.out.println((i + 1) + ": " + visits.get(i));
        }
    }

     public boolean deleteVisit(int index) {
        if (index < 1 || index > visits.size()) return false;
        visits.remove(index - 1);
        return true;
    }

    public void deleteLastFiveVisits() {
        int count = Math.min(5, visits.size());
        for (int i = 0; i < count; i++) {
            visits.remove(visits.size() - 1);
        }
    }
}
