package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.service.DoctorManager;
import com.hospital.model.Doctor;
import java.util.List;
import java.util.Scanner;

public class ManageDoctorsCommand implements ICommand {
    private DoctorManager doctorManager;
    private Scanner scanner;

    public ManageDoctorsCommand(DoctorManager doctorManager, Scanner scanner) {
        this.doctorManager = doctorManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n=== Manage Doctors ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. Remove Doctor");
            System.out.println("3. View All Doctors");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addDoctor();
                    break;
                case "2":
                    removeDoctor();
                    break;
                case "3":
                    viewAllDoctors();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addDoctor() {
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();

        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Specialty: ");
        String specialty = scanner.nextLine().trim();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();

        System.out.print("Enter Contact Info: ");
        String contactInfo = scanner.nextLine().trim();

        Doctor doctor = new Doctor(doctorId, name, specialty, department, contactInfo);
        doctorManager.addDoctor(doctor);
    }

    private void removeDoctor() {
        System.out.print("Enter Doctor ID to remove: ");
        String doctorId = scanner.nextLine().trim();
        doctorManager.removeDoctor(doctorId);
    }

    private void viewAllDoctors() {
        List<Doctor> doctors = doctorManager.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }

        System.out.println("\n=== All Doctors ===");
        System.out.println("Total Doctors: " + doctors.size());
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    @Override
    public String getToken() {
        return "managedoctors";
    }
}
