package com.hospital.repository;

import com.hospital.model.Diagnosis;

public interface IDiagnosisRepository {
    void insertDiagnosis(String patientId, Diagnosis diagnosis);
}
