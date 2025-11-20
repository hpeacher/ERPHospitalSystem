package com.hospital.service;

import com.hospital.model.*;

public class NurseWorkflowService {
    private final Hospital hospital;
    private final VitalsRecorder vitalsRecorder;
    private final ChecklistProcessor checklistProcessor;

    public NurseWorkflowService(Hospital hospital, ChecklistProcessor checklistProcessor) {
        this.hospital = hospital;
        this.vitalsRecorder = new VitalsRecorder();
        this.checklistProcessor = checklistProcessor;
    }

    public void handleDischargeInitiation(Nurse nurse, PatientRecord patientRecord) {
        if (!hospital.getPatientsAssignedTo(nurse.getId()).contains(patientRecord.getPatientId()))
            return;

        System.out.println(
                "Nurse " + nurse.getId() + " notified: Discharge initiated for " + patientRecord.getPatientId());
        VisitRecord record = vitalsRecorder.recordVitals(patientRecord);
        if (record != null) {
            if (record.getDischargeChecklist() == null) {
                checklistProcessor.initiateChecklist(record);
            }
            checklistProcessor.completeChecklist(patientRecord, record, "Notes for " + patientRecord.getPatientId(),
                    nurse.getName());
        }
    }

    public void handleDischargeCompletion(Nurse nurse, String patientId) {
        if (!hospital.getPatientsAssignedTo(nurse.getId()).contains(patientId))
            return;

        System.out.println("Nurse " + nurse.getId() + " notified: Discharge completed for " + patientId);
        hospital.dischargePatient(patientId);
    }
}