package com.hospital.model;

import com.hospital.service.IDiagnosisService;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialty;

    public Doctor(int doctorId, String name, String specialty) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void diagnose(PatientRecord patient, String description, String prescription, IDiagnosisService service) {
        service.saveDiagnosis(patient.getPatientId(), description, prescription);
    }
}
