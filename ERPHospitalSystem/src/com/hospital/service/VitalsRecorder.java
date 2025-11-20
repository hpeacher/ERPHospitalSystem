package com.hospital.service;

import com.hospital.model.VisitRecord;

public class VitalsRecorder {

    public VitalsRecorder() {

    }

    public VisitRecord recordVitals(String patientId) {
        // Implementation for recording vitals
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setVitals("Sample Vitals Data for Patient ID: " + patientId);
        return visitRecord;
    }
}
