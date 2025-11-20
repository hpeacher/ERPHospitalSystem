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

    public void handleDischargeInitiation(Nurse nurse, String patientId) {
        if (!hospital.getPatientsAssignedTo(nurse.getId()).contains(patientId))
            return;

        System.out.println("Nurse " + nurse.getId() + " notified: Discharge initiated for " + patientId);
        VisitRecord record = vitalsRecorder.recordVitals(patientId);
        if (record != null) {
            checklistProcessor.initiateChecklist(record);
            checklistProcessor.completeChecklist(record, "Notes for " + patientId, nurse.getName());
        }
    }

    public void handleDischargeCompletion(Nurse nurse, String patientId) {
        if (!hospital.getPatientsAssignedTo(nurse.getId()).contains(patientId))
            return;

        System.out.println("Nurse " + nurse.getId() + " notified: Discharge completed for " + patientId);
        hospital.dischargePatient(patientId);
    }
}