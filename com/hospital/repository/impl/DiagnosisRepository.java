package com.hospital.repository.impl;

import com.hospital.repository.IDiagnosisRepository;
import com.hospital.repository.IFileStorage;
import com.hospital.model.Diagnosis;

public class DiagnosisRepository implements IDiagnosisRepository {
    private IFileStorage fileStorage;
    private static int counter = 1;

    public DiagnosisRepository(IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void insertDiagnosis(String patientId, Diagnosis diagnosis) {
        String record = "PatientID:" + patientId + "|" + diagnosis.toFileString();
        fileStorage.writeToFile(record);
    }
}