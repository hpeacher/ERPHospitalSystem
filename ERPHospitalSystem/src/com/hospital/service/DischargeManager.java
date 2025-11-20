package com.hospital.service;

import com.hospital.model.*;
import com.hospital.repository.IPatientFileManager;

public class DischargeManager {
    private BillingProcessor billingProcessor;
    private IPatientFileManager patientFileManager;
    private Hospital hospital;
    private final NurseWorkflowService nurseWorkflow;

    public DischargeManager(Hospital hospital,
            BillingProcessor billingProcessor,
            IPatientFileManager patientFileManager) {
        this.hospital = hospital;
        this.billingProcessor = billingProcessor;
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
        nurseWorkflow.handleDischargeInitiation(nurse, patientId);
    }

    public void checklistCompleted(VisitRecord visitRecord) {
        PatientRecord patientRecord = patientFileManager.getPatientRecord(visitRecord.getPatientId());
        Invoice invoice = billingProcessor.generateInvoice(visitRecord.getPatientId(), visitRecord.getId(),
                1000.0,
                patientRecord.getInsurance());
        visitRecord.setInvoiceId(invoice.getId());
        patientRecord.addVisit(visitRecord);
        patientFileManager.postPatientRecord(patientRecord);
        System.out.println("Discharge summary generated for Patient ID: " + patientRecord.getPatientId());
    }
}