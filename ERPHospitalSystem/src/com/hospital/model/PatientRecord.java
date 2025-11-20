package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

public class PatientRecord {
    private String patientId;
    private PatientAdministrativeInfo administrativeInfo;
    private PatientMedicalInfo medicalInfo;
    private ArrayList<VisitRecord> visits;
    private String insurance;

    private List<Appointment> appointments = new ArrayList<>();

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appt) {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(appt);
    }

    // Constructor
    public PatientRecord(String patientId,
            PatientAdministrativeInfo administrativeInfo,
            PatientMedicalInfo medicalInfo,
            String insurance) {

        this.patientId = patientId;
        this.administrativeInfo = administrativeInfo;
        this.medicalInfo = medicalInfo;
        this.visits = new ArrayList<>();
        this.insurance = insurance;

        // IMPORTANT
        this.appointments = new ArrayList<>();
    }

    public PatientRecord() {
        this.visits = new ArrayList<>();
        this.appointments = new ArrayList<>(); // IMPORTANT
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

    public void addVisit(VisitRecord visit) {
        this.visits.add(visit);
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public VisitRecord getMostRecentVisitRecord() {
        return visits.get(visits.size() - 1);

    public String getMedicalHistory() {
        return "Retrieving medical history for: " + administrativeInfo.getName();
    }
}
