package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.model.LabOrder;
import com.hospital.service.ILabOrderService;
import java.util.List;
import java.util.Scanner;

public class ProcessLabOrderCommand implements ICommand {
    private ILabOrderService labOrderService;
    private Scanner scanner;

    public ProcessLabOrderCommand(ILabOrderService labOrderService, Scanner scanner) {
        this.labOrderService = labOrderService;
        this.scanner = scanner;
    }

    @Override
    public String getToken() {
        return "processlaborder";
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n=== Process Lab Orders ===");
            System.out.println("1. Order Test (Doctor)");
            System.out.println("2. Enter Test Results (Lab Technician)");
            System.out.println("3. Review & Approve Results (Doctor)");
            System.out.println("4. View Pending Orders");
            System.out.println("5. Cancel Order");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleDoctorOrder();
                    break;
                case "2":
                    handleLabEntry();
                    break;
                case "3":
                    handleDoctorApproval();
                    break;
                case "4":
                    viewPendingOrders();
                    break;
                case "5":
                    cancelOrder();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleDoctorOrder() {
        System.out.println("\n--- Order Lab Test ---");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();

        System.out.print("Enter Test Name (e.g., Blood Test, X-Ray, MRI): ");
        String testName = scanner.nextLine().trim();

        LabOrder order = labOrderService.createOrder(patientId, doctorId, testName);
        System.out.println("Success! Lab order created with ID: " + order.getOrderId());
    }

    private void handleLabEntry() {
        System.out.println("\n--- Enter Test Results ---");

        List<LabOrder> pendingOrders = labOrderService.getPendingOrders();
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending lab orders for result entry.");
            return;
        }

        System.out.println("\nPending Orders:");
        for (LabOrder order : pendingOrders) {
            System.out.println("Order ID: " + order.getOrderId() + 
                             " | Patient: " + order.getPatientId() + 
                             " | Test: " + order.getTestName());
        }

        System.out.print("\nEnter Order ID: ");
        String orderId = scanner.nextLine().trim();

        System.out.print("Enter Test Results: ");
        String results = scanner.nextLine().trim();

        System.out.print("Enter Technician Notes (optional): ");
        String notes = scanner.nextLine().trim();

        if (labOrderService.submitResults(orderId, results, notes)) {
            System.out.println("Results successfully submitted!");
        } else {
            System.out.println("Failed to submit results.");
        }
    }

    private void handleDoctorApproval() {
        System.out.println("\n--- Review & Approve Results ---");

        List<LabOrder> reviewOrders = labOrderService.getPendingOrdersForReview();
        if (reviewOrders.isEmpty()) {
            System.out.println("No lab orders pending review.");
            return;
        }

        System.out.println("\nOrders Ready for Review:");
        for (LabOrder order : reviewOrders) {
            System.out.println("\nOrder ID: " + order.getOrderId());
            System.out.println("Patient: " + order.getPatientId());
            System.out.println("Test: " + order.getTestName());
            System.out.println("Results: " + order.getResults());
            System.out.println("Notes: " + order.getTechnicianNotes());
            if (order.isCritical()) {
                System.out.println("*** CRITICAL RESULTS - REQUIRES IMMEDIATE ATTENTION ***");
            }
            System.out.println("---");
        }

        System.out.print("\nEnter Order ID to approve: ");
        String orderId = scanner.nextLine().trim();

        if (labOrderService.approveOrder(orderId)) {
            System.out.println("Order approved and finalized!");
        } else {
            System.out.println("Failed to approve order.");
        }
    }

    private void viewPendingOrders() {
        System.out.println("\n--- All Pending Orders ---");

        List<LabOrder> pendingOrders = labOrderService.getPendingOrders();
        List<LabOrder> reviewOrders = labOrderService.getPendingOrdersForReview();

        if (pendingOrders.isEmpty() && reviewOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        if (!pendingOrders.isEmpty()) {
            System.out.println("\nAwaiting Lab Collection:");
            for (LabOrder order : pendingOrders) {
                System.out.println(order);
            }
        }

        if (!reviewOrders.isEmpty()) {
            System.out.println("\nAwaiting Doctor Review:");
            for (LabOrder order : reviewOrders) {
                System.out.println(order);
            }
        }
    }

    private void cancelOrder() {
        System.out.println("\n--- Cancel Order ---");
        System.out.print("Enter Order ID to cancel: ");
        String orderId = scanner.nextLine().trim();

        if (labOrderService.cancelOrder(orderId)) {
            System.out.println("Order canceled successfully.");
        } else {
            System.out.println("Failed to cancel order.");
        }
    }
}
