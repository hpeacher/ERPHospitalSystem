package com.hospital.model;

import com.hospital.service.IDiagnosisService;
import java.util.ArrayList;

public class Doctor extends Employee {
    private String specialty;
    private ArrayList<String> assignedPatients;

    public Doctor(String doctorId, String name, String specialty, String department, String contactInfo) {
        super(doctorId, name, "Doctor", department, contactInfo);
        this.specialty = specialty;
        this.assignedPatients = new ArrayList<>();
    }

    public Doctor(int doctorId, String name, String specialty) {
        super(String.valueOf(doctorId), name, "Doctor", "General", "N/A");
        this.specialty = specialty;
        this.assignedPatients = new ArrayList<>();
    }

    public String getDoctorId() {
        return employeeId;
    }

    public void setDoctorId(String doctorId) {
        this.employeeId = doctorId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void assignPatient(String patientId) {
        if (!assignedPatients.contains(patientId)) {
            assignedPatients.add(patientId);
        }
    }

    public void unassignPatient(String patientId) {
        assignedPatients.remove(patientId);
    }

    public boolean hasActivePatients() {
        return !assignedPatients.isEmpty();
    }

    public ArrayList<String> getAssignedPatients() {
        return assignedPatients;
    }

    public void diagnose(PatientRecord patient, String description, String prescription, IDiagnosisService service) {
        service.saveDiagnosis(patient.getPatientId(), description, prescription);
    }

    @Override
    public String toString() {
        return "Doctor ID: " + employeeId + " | Name: " + name + " | Specialty: " + specialty +
               " | Department: " + department + " | Contact: " + contactInfo +
               " | Active Patients: " + assignedPatients.size();
    }
}
