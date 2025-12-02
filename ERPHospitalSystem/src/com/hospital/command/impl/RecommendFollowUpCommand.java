package com.hospital.command.impl;

import java.util.Scanner;

import com.hospital.command.ICommand;
import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;
import com.hospital.service.FollowUpAnalyzer;
import com.hospital.service.AppointmentScheduler;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.model.Appointment;

public class RecommendFollowUpCommand implements ICommand {

    private final PatientFileManager fileManager;
    private final FollowUpAnalyzer analyzer;
    private final AppointmentScheduler scheduler;
    private final Scanner sc;

    public RecommendFollowUpCommand(PatientFileManager fileManager,
                                    FollowUpAnalyzer analyzer,
                                    AppointmentScheduler scheduler,
                                    Scanner sc) {
        this.fileManager = fileManager;
        this.analyzer = analyzer;
        this.scheduler = scheduler;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        return "recommend_followup";
    }

    @Override
    public void execute() {
        
        System.out.print("Enter patient ID: ");
        String patientId = sc.nextLine();

        PatientRecord record = fileManager.getPatientRecord(patientId);
        if (record == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (record.getVisits().isEmpty()) {
            System.out.println("No visits found for this patient.");
            return;
        }

        // Get most recent visit
        VisitRecord visit = record.getVisits().get(record.getVisits().size() - 1);

        String result = analyzer.analyze(visit);

     // If analyzer cannot determine (missing vitals/notes), use a simple default recommendation
     if (result.equals("INSUFFICIENT_DATA")) {
         result = "Recommend routine follow-up in 2 weeks.";
     }
        System.out.println("Recommended follow-up action:");
        System.out.println(result);
        
        // Save recommendation into visit record
        visit.setFollowUpRecommendation(result);
        fileManager.postPatientRecord(record);

        // Ask user if they want to schedule now
        System.out.print("Would you like to schedule a follow-up appointment now? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {

            System.out.print("Enter doctor name: ");
            String doc = sc.nextLine();

            System.out.print("Enter follow-up date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Enter follow-up time (HH:MM): ");
            String time = sc.nextLine();

            try {
                Appointment appt = scheduler.scheduleAppointment(patientId, doc, date, time);
                System.out.println("Follow-up appointment scheduled with ID: " + appt.getAppointmentId());
            } catch (Exception e) {
                System.out.println("Could not schedule appointment: " + e.getMessage());
            }
        }

        System.out.println("Recommendation logged.");
    }
}
