package com.hospital.service;

import java.util.ArrayList;

import com.hospital.model.VisitRecord;

public class Nurse {
    private String nurseId;
    private String name;
    private ArrayList<String> assignedPatients;
    private Hospital hospital;
    private VitalsRecorder vitalsRecorder;
    private ChecklistProcessor checklistProcessor;

    public Nurse(String nurseId, String name, Hospital hospital) {
        this.nurseId = nurseId;
        this.name = name;
        this.assignedPatients = new ArrayList<String>();
        this.vitalsRecorder = new VitalsRecorder();
        this.checklistProcessor = new ChecklistProcessor();
        this.hospital = hospital;
    }

    public String getNurseId() {
        return nurseId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAssignedPatients() {
        return assignedPatients;
    }

    public boolean assignPatient(String patientId) {
        if (!assignedPatients.contains(patientId)) {
            assignedPatients.add(patientId);
            System.out.println("Patient " + patientId + " has been assigned to Nurse " + nurseId + ".");
            return true;
        } else {
            System.out.println("Patient " + patientId + " is already assigned to Nurse " + nurseId + ".");
            return false;
        }
    }

    public boolean unassignPatient(String patientId) {
        if (assignedPatients.contains(patientId)) {
            assignedPatients.remove(patientId);
            System.out.println("Patient " + patientId + " has been unassigned from Nurse " + nurseId + ".");
            return true;
        } else {
            System.out.println("Patient " + patientId + " is not assigned to Nurse " + nurseId + ".");
            return false;
        }
    }

    public void notifyDischargeInitiated(String patientId) {
        if (assignedPatients.contains(patientId)) {
            System.out.println(
                    "Nurse " + nurseId + " notified: Discharge process initiated for Patient " + patientId + ".");
            VisitRecord visitRecord = vitalsRecorder.recordVitals(patientId);
            if (visitRecord != null) {
                System.out.println("Nurse " + nurseId + " has recorded vitals for Patient " + patientId + ".");
                checklistProcessor.initiateChecklist(visitRecord);
                checklistProcessor.completeChecklist(visitRecord, "Notes for Patient " + patientId, this.name);
            } else {
                System.out
                        .println("Nurse " + nurseId + " could not record vitals for Patient " + patientId + ".");
            }
        }

    }

    public void dischargeCompleted(String patientId) {
        if (assignedPatients.contains(patientId)) {
            System.out.println(
                    "Nurse " + nurseId + " notified: Discharge process completed for Patient " + patientId + ".");
            assignedPatients.remove(patientId);
            hospital.patientDischarged(patientId);
        }

    }
}
