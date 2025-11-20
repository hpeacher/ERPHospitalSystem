package com.hospital.service;

import com.hospital.model.*;
import com.hospital.repository.IPatientFileManager;

public class DischargeManager {
    private IPatientFileManager patientFileManager;
    private Hospital hospital;
    private final NurseWorkflowService nurseWorkflow;

    public DischargeManager(Hospital hospital,
            IPatientFileManager patientFileManager) {
        this.hospital = hospital;
        this.patientFileManager = patientFileManager;

        ChecklistProcessor checklistProcessor = new ChecklistProcessor(this::checklistCompleted);
        this.nurseWorkflow = new NurseWorkflowService(hospital, checklistProcessor);
    }

    public void initiateDischarge(String patientId) {
        // Implementation here
        Nurse nurse = hospital.findNurseByPatientId(patientId);
        if (nurse == null) {
            System.out.println("Nurse assigned to patient not found, DISCHARGE FAILED.");
            return;
        }
        PatientRecord patientRecord = patientFileManager.getPatientRecord(patientId);
        nurseWorkflow.handleDischargeInitiation(nurse, patientRecord);
    }

    public void checklistCompleted(PatientRecord patientRecord) {
        if (patientRecord == null) {
            System.out.println("Discharge Summary not generated");
            return;
        }

        patientFileManager.postPatientRecord(patientRecord);
        System.out.println("Discharge summary generated for Patient ID: " + patientRecord.getPatientId());

    }
}