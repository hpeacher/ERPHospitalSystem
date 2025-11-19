package com.hospital.command.impl;

import java.util.ArrayList;
import java.util.Scanner;
import com.hospital.command.ICommand;
import com.hospital.model.PatientAdministrativeInfo;
import com.hospital.model.PatientMedicalInfo;
import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;
import com.hospital.repository.impl.PatientFileManager;

public class ViewPatientRecordCommand implements ICommand{

    private final PatientFileManager fileManager;
    private final Scanner sc;

    public ViewPatientRecordCommand(PatientFileManager fileManager, Scanner sc){
        this.fileManager = fileManager;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        return "viewPatientRecord";
    }

    @Override
    public void execute() {
        System.out.println("=== View Patient Record ===");

        System.out.println("Enter Patient Id: ");
        String patientId = sc.nextLine();

        PatientRecord record = fileManager.getPatientRecord(patientId);

        if (record == null) {
            System.out.println("Patient record not found.");
            return;
        }

        System.out.println("Patient ID: " + record.getPatientId());
        PatientAdministrativeInfo adminInfo = record.getAdministrativeInfo();
        System.out.println("Name: " + adminInfo.getName());
        System.out.println("Date of Birth: " + adminInfo.getDateOfBirth());
        System.out.println("Phone: " + adminInfo.getPhoneNumber());
        System.out.println("Address: " + adminInfo.getAddress());

        PatientMedicalInfo medInfo = record.getMedicalInfo();
        System.out.println("Gender: " + medInfo.getGender());
        System.out.println("Blood Type: " + medInfo.getBloodType());
        System.out.println("Height: " + medInfo.getHeight());
        System.out.println("Weight: " + medInfo.getWeight());
        System.out.println("Insurance: " + record.getInsurance());
    
    }
}
