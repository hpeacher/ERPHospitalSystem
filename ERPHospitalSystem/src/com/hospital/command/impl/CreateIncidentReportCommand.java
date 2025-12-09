package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.service.IncidentReportController;

import java.util.Scanner;

public class CreateIncidentReportCommand implements ICommand {

    private final IncidentReportController controller;
    private final Scanner scanner;

    public CreateIncidentReportCommand(IncidentReportController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public String getToken() {
        return "createIncidentReport";
    }

    @Override
    public void execute() {
        System.out.println("\n=== Create Incident Report ===");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        System.out.print("Enter Reporter Employee ID: ");
        String reporterId = scanner.nextLine().trim();

        System.out.print("Enter Incident Description: ");
        String description = scanner.nextLine().trim();

        try {
            boolean success = controller.createIncidentReport(patientId, reporterId, description);

            if (success) {
                System.out.println("\nIncident report successfully created.");
            } else {
                System.out.println("\nERROR: Failed to create incident report.");
            }
        } catch (Exception e) {
            System.out.println("\nERROR: " + e.getMessage());
        }

        System.out.println("==============================\n");
    }
}




