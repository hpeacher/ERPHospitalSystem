package com.hospital.service;

import com.hospital.model.Diagnosis;
import com.hospital.repository.IDiagnosisRepository;

public interface IDiagnosisService {
    void saveDiagnosis(String patientId, String description, String prescription);
}

class DiagnosisService implements IDiagnosisService {
    private IDiagnosisRepository repository;
    private static int diagnosisCounter = 1;

    public DiagnosisService(IDiagnosisRepository repository) {
        this.repository = repository;
    }

    public void saveDiagnosis(String patientId, String description, String prescription) {
        Diagnosis diagnosis = new Diagnosis(diagnosisCounter++, description, prescription);
        repository.insertDiagnosis(patientId, diagnosis);
        System.out.println("Diagnosis successfully recorded for patient ID: " + patientId);
    }
}
