package com.hospital.command.impl;

import java.util.Scanner;
import com.hospital.command.ICommand;
import com.hospital.model.PatientRecord;
import com.hospital.repository.impl.PatientFileManager;

public class DeletePatientRecordCommand implements ICommand{

    private final PatientFileManager fileManager;
    private final Scanner sc;

    public DeletePatientRecordCommand(PatientFileManager fileManager, Scanner sc){
        this.fileManager = fileManager;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        return "deletePatientRecord";
    }

    @Override
    public void execute() {
        System.out.println("=== Delete Patient Record ===");

        System.out.println("Enter Patient Id: ");
        String patientId = sc.nextLine();

        PatientRecord record = fileManager.getPatientRecord(patientId);
        
        fileManager.postPatientRecord(record);  


        System.out.println("What would you like to delete?");
        System.out.println("1. Administrative Info");
        System.out.println("2. Medical Info");
        System.out.println("3. Visit record");
        System.out.println("4. Entire Patient Record");

        String choice = sc.nextLine();

        switch (choice){
            case "1":
                confirmDeleteAdministrative(record);
                break;

            case "2":
                confirmDeleteMedical(record);
                break;
            case "3":
                handleDeleteVisitHistory(record);
                break;
            
            case "4":
                deleteFullRecord(patientId);
                break;
            }   
            fileManager.postPatientRecord(record);     
        }


    private void deleteFullRecord(String patientId) {
         System.out.println("Delete ALL administrative info? (y/n)");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("y")) {
            if(fileManager.deletePatientRecord(patientId)){
                System.out.println("Patient record deleted.");
            } else {
                System.out.println("Patient record not found.");
            }
        }
    }

    private void handleDeleteVisitHistory(PatientRecord record){
        System.out.println("Visit History Options:");
        System.out.println("1 - Delete ALL visit history");
        System.out.println("2 - Delete a specific visit");
        System.out.println("3 - Delete most recent visit");

        String option = sc.nextLine();

        switch (option){
            case "1":
                System.out.println("Are you sure you want to delete all the patients visit records?");
                System.out.println("1. Yes 2. No");
                int choice = sc.nextInt();
                if (choice == 1){
                    record.deleteVisitRecord();
                    System.out.println("Visit record deleted");
                }
                else{
                    System.out.println("Canceled");
                }
                break;
            case "2":
                deleteSpecificVisit(record);
                break;
            
            case "3":
                record.deleteLastFiveVisits();
                break;
            
            default:
                System.out.println("Invalid option.");
        }
    }
    private void deleteSpecificVisit(PatientRecord record) {
        System.out.println("Visit List:");
        record.printVisitHistory();

        System.out.println("Enter visit number to delete:");
        int visitIndex = Integer.parseInt(sc.nextLine());

        boolean removed = record.deleteVisit(visitIndex);
        if (removed) {
            System.out.println("Visit deleted.");
        } else {
            System.out.println("Invalid visit number.");
        }
    }

    private void confirmDeleteAdministrative(PatientRecord record) {
        System.out.println("Delete ALL administrative info? (y/n)");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("y")) {
            record.setAdministrativeInfo(null);
            System.out.println("Administrative info deleted.");
        }
    }

    private void confirmDeleteMedical(PatientRecord record) {
        System.out.println("Delete ALL medical info? (y/n)");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("y")) {
            record.setMedicalInfo(null);
            System.out.println("Medical info deleted.");
        }
    }

}
