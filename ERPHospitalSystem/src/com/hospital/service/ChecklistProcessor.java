package com.hospital.service;

import java.util.function.Consumer;

import com.hospital.model.*;

public class ChecklistProcessor {
    private final Consumer<PatientRecord> onChecklistCompleted;

    public ChecklistProcessor(Consumer<PatientRecord> onChecklistCompleted) {
        this.onChecklistCompleted = onChecklistCompleted;
    }

    public void initiateChecklist(VisitRecord visitRecord) {
        // Implementation for initiating the checklist
        visitRecord.setDischargeChecklist(new DischargeChecklist());
    }

    public boolean completeChecklist(PatientRecord patientRecord, VisitRecord visitRecord, String notes,
            String signature) {
        // Implementation for completing the checklist
        visitRecord.setNotes(notes);
        DischargeChecklist checklist = visitRecord.getDischargeChecklist();
        if (checklist != null) {
            checklist.setNotesCompleted(true);
            checklist.setSignature(signature);
            patientRecord.updateMostRecentVisitRecord(visitRecord);
            onChecklistCompleted.accept(patientRecord);
            return true;
        }
        return false;
    }
}