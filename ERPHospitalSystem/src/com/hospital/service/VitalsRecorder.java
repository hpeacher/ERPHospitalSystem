package com.hospital.service;

import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;

public class VitalsRecorder {

    public VitalsRecorder() {

    }

    public VisitRecord recordVitals(PatientRecord patientRecord) {
        // Implementation for recording vitals
        VisitRecord visit = patientRecord.getMostRecentVisitRecord();
        if (visit == null) {
            visit = new VisitRecord(patientRecord.getPatientId());
        }

        visit.setVitals("Sample Vitals Data for Patient ID: " + patientRecord.getPatientId());
        return visit;
    }
}
