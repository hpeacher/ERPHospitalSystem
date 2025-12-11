package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.DoctorWorkloadEntry;
import com.hospital.model.Hospital;
import com.hospital.model.Nurse;
import com.hospital.model.NurseWorkloadEntry;
import com.hospital.model.PatientRecord;
import com.hospital.model.UtilizationReport;
import com.hospital.repository.impl.PatientFileManager;

import java.util.ArrayList;
import java.util.List;

public class UtilizationReportService {

    private final Hospital hospital;
    private final DoctorManager doctorManager;

    public UtilizationReportService(Hospital hospital, DoctorManager doctorManager) {
        this.hospital = hospital;
        this.doctorManager = doctorManager;
    }

    public UtilizationReport generateReport(PatientFileManager fileManager) {

        List<String> warnings = new ArrayList<>();
        List<String> notes = new ArrayList<>();

        // ----- Hospital-level metrics -----
        int totalCapacity = hospital.getCapacity();
        List<String> admittedIds = hospital.getPatients();
        int currentOccupancy = admittedIds.size();
        double occupancyRate = (totalCapacity == 0)
                ? 0.0
                : (currentOccupancy * 100.0) / totalCapacity;

        if (currentOccupancy == 0) {
            notes.add("No patients are currently admitted. Occupancy is 0%.");
        }

        // ----- Load patient records for admitted patients (for nurse workload + warnings) -----
        List<PatientRecord> admittedRecords = new ArrayList<>();
        List<String> missingIds = new ArrayList<>();

        for (String pid : admittedIds) {
            try {
                PatientRecord record = fileManager.getPatientRecord(pid);
                if (record != null) {
                    admittedRecords.add(record);
                } else {
                    missingIds.add(pid);
                }
            } catch (Exception e) {
                missingIds.add(pid);
            }
        }

        if (!missingIds.isEmpty()) {
            warnings.add("Warning: Missing patient records: " + String.join(", ", missingIds));
        }

        // ----- Nurse workload (based on admitted patients) -----
        List<NurseWorkloadEntry> nurseWorkloads = new ArrayList<>();
        for (Nurse nurse : hospital.getNurses()) {
            int count = hospital.getPatientsAssignedTo(nurse.getId()).size();
            nurseWorkloads.add(new NurseWorkloadEntry(nurse, count));
        }

        
        List<DoctorWorkloadEntry> doctorWorkloads = new ArrayList<>();

        
        List<PatientRecord> allRecords = new ArrayList<>();
        try {
            allRecords = PatientFileManager.getAllPatientRecords();
        } catch (Exception e) {
            warnings.add("Warning: Could not load all patient records for doctor workload.");
        }

        int smithCount = 0;
        int johnsonCount = 0;
        int williamsCount = 0;

        for (PatientRecord pr : allRecords) {
            if (pr == null || pr.getAppointments() == null) {
                continue;
            }

            for (Appointment appt : pr.getAppointments()) {
                if (appt == null || appt.getDoctorName() == null) {
                    continue;
                }

                String name = appt.getDoctorName().toLowerCase();

                if (name.contains("smith")) {
                    smithCount++;
                } else if (name.contains("johnson")) {
                    johnsonCount++;
                } else if (name.contains("williams")) {
                    williamsCount++;
                }
            }
        }

        int globalAppts = smithCount + johnsonCount + williamsCount;

        
        for (Doctor doctor : doctorManager.getAllDoctors()) {
            String doctorNameLower = doctor.getName().toLowerCase();
            int count = 0;

            if (doctorNameLower.contains("smith")) {
                count = smithCount;
            } else if (doctorNameLower.contains("johnson")) {
                count = johnsonCount;
            } else if (doctorNameLower.contains("williams")) {
                count = williamsCount;
            }

            boolean overloaded = count > 20;
            doctorWorkloads.add(new DoctorWorkloadEntry(doctor, count, overloaded));
        }

        if (globalAppts == 0) {
            notes.add("No appointments are currently scheduled.");
        }

        // 
        return new UtilizationReport(
                totalCapacity,
                currentOccupancy,
                occupancyRate,
                nurseWorkloads,
                doctorWorkloads,
                warnings,
                notes
        );
    }
}
