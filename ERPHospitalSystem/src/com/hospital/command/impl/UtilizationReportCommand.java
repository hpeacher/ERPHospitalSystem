package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.model.Doctor;
import com.hospital.model.DoctorWorkloadEntry;
import com.hospital.model.Nurse;
import com.hospital.model.NurseWorkloadEntry;
import com.hospital.model.UtilizationReport;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.service.UtilizationReportService;

import java.util.Scanner;

public class UtilizationReportCommand implements ICommand {

    private final UtilizationReportService service;
    private final PatientFileManager patientFileManager;
    @SuppressWarnings("unused")
    private final Scanner sc;

    public UtilizationReportCommand(UtilizationReportService service,
                                    PatientFileManager fileManager,
                                    Scanner sc) {
        this.service = service;
        this.patientFileManager = fileManager;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        
        return "generate_hospital_report";
    }

    @Override
    public void execute() {
        UtilizationReport report = service.generateReport(patientFileManager);
        print(report);
    }

    private void print(UtilizationReport r) {
        System.out.println("===== Hospital Utilization Report =====");
        System.out.printf("Capacity: %d%n", r.getTotalCapacity());
        System.out.printf("Admitted: %d%n", r.getCurrentOccupancy());
        System.out.printf("Occupancy: %.2f%%%n", r.getOccupancyRate());

        System.out.println("\n-- Nurse Workload --");
        for (NurseWorkloadEntry n : r.getNurseWorkloads()) {
            Nurse nurse = n.getNurse();
            System.out.printf("%s -> %d patients%n",
                    nurse.getName(), n.getActivePatientCount());
        }

        System.out.println("\n-- Doctor Workload --");
        for (DoctorWorkloadEntry d : r.getDoctorWorkloads()) {
            Doctor doc = d.getDoctor();
            String overload = d.isOverloaded() ? " (HIGH)" : "";
            System.out.printf("%s -> %d appointments%s%n",
                    doc.getName(), d.getAppointmentCount(), overload);
        }

        if (!r.getWarnings().isEmpty()) {
            System.out.println("\nWarnings:");
            r.getWarnings().forEach(w -> System.out.println(" - " + w));
        }

        if (!r.getNotes().isEmpty()) {
            System.out.println("\nNotes:");
            r.getNotes().forEach(n -> System.out.println(" - " + n));
        }

        System.out.println("=======================================\n");
    }
}
