package com.hospital.command.impl;

import java.util.List;
import java.util.Scanner;

import com.hospital.command.ICommand;
import com.hospital.model.Appointment;
import com.hospital.model.PatientRecord;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.service.AppointmentScheduler;

public class ManageAppointmentsCommand implements ICommand {

    private final PatientFileManager fileManager;
    private final AppointmentScheduler scheduler;
    private final Scanner sc;

    public ManageAppointmentsCommand(
            PatientFileManager fileManager,
            AppointmentScheduler scheduler,
            Scanner sc) {
        this.fileManager = fileManager;
        this.scheduler = scheduler;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        return "manage_appointments";
    }

    @Override
    public void execute() {

        System.out.print("Enter patient ID: ");
        String patientId = sc.nextLine();

        PatientRecord record = fileManager.getPatientRecord(patientId);

        if (record == null) {
            System.out.println("No patient found.");
            return;
        }

        while (true) {
            System.out.println("\n--- Manage Appointments Menu ---");
            System.out.println("1. View appointments");
            //System.out.println("2. Schedule new appointment");
            System.out.println("2. Cancel appointment");
            System.out.println("3. Update appointment");
            System.out.println("4. Exit to main menu");
            System.out.print("Select an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewAppointments(record);
                    break;
                /*case "2":
                    createAppointment(patientId);
                    break;*/
                case "2":
                    cancelAppointment(record);
                    break;
                case "3":
                    updateAppointment(record);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    // ------- VIEW APPOINTMENTS -------
    private void viewAppointments(PatientRecord record) {
        List<Appointment> appointments = record.getAppointments();
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        System.out.println("\nAppointments:");
        for (Appointment ap : appointments) {
            System.out.println("ID: " + ap.getAppointmentId()
                    + ", Doctor: " + ap.getDoctorName()
                    + ", Date: " + ap.getDate()
                    + ", Time: " + ap.getTime());
        }
    }

    // ------- CREATE APPOINTMENT -------
    /*private void createAppointment(String patientId) {
        try {
            System.out.print("Enter doctor name: ");
            String doc = sc.nextLine();
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter time (HH:MM): ");
            String time = sc.nextLine();

            Appointment appt = scheduler.scheduleAppointment(patientId, doc, date, time);
            System.out.println("Appointment scheduled! ID: " + appt.getAppointmentId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Suggesting alternative times:");
            System.out.println("- " + "10:00");
            System.out.println("- " + "11:30");
            System.out.println("- " + "13:00");
        }
    }*/

    
   
    // ------- CANCEL APPOINTMENT -------
    private void cancelAppointment(PatientRecord record) {
        List<Appointment> list = record.getAppointments();
        if (list == null || list.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return;
        }

        viewAppointments(record);

        System.out.print("Enter appointment ID to cancel: ");
        String id = sc.nextLine();

        boolean removed = list.removeIf(a -> a.getAppointmentId().equals(id));


        if (removed) {
            fileManager.postPatientRecord(record);
            System.out.println("Appointment canceled.");
        } else {
            System.out.println("Appointment not found.");
        }
    }
    
    private void updateAppointment(PatientRecord record) {
        List<Appointment> list = record.getAppointments();
        if (list == null || list.isEmpty()) {
            System.out.println("No appointments to update.");
            return;
        }

        viewAppointments(record);

        System.out.print("Enter appointment ID to update: ");
        String id = sc.nextLine(); 

        Appointment appt = list.stream()
            .filter(a -> a.getAppointmentId().equals(id))  
            .findFirst()
            .orElse(null);

        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.print("Enter new doctor name (leave blank to keep \"" +
                appt.getDoctorName() + "\"): ");
        String newDoc = sc.nextLine();
        if (!newDoc.isEmpty()) appt.setDoctorName(newDoc);

        System.out.print("Enter new date (leave blank to keep " +
                appt.getDate() + "): ");
        String newDate = sc.nextLine();
        if (!newDate.isEmpty()) appt.setDate(newDate);

        System.out.print("Enter new time (leave blank to keep " +
                appt.getTime() + "): ");
        String newTime = sc.nextLine();
        if (!newTime.isEmpty()) appt.setTime(newTime);

        fileManager.postPatientRecord(record);

        System.out.println("Appointment updated successfully.");
    }


}
