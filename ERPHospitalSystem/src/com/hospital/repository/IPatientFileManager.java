package com.hospital.repository;

import com.hospital.model.PatientRecord;

public interface IPatientFileManager {
    boolean postPatientRecord(PatientRecord record);

    PatientRecord getPatientRecord(String patientId);

    boolean deletePatientRecord(String patientId);
}