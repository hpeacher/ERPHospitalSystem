package com.hospital.command.impl;

import java.util.ArrayList;
import java.util.Scanner;

import com.hospital.model.PatientAdministrativeInfo;
import com.hospital.model.PatientMedicalInfo;
import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;
import com.hospital.repository.impl.PatientFileManager;

public class ViewPatientRecordTest {
    public static void main(String[] args) {


       PatientAdministrativeInfo ai = new PatientAdministrativeInfo("p5", "John Doe", "1990-01-01", "123-456-7890", "123 Main St");
       PatientMedicalInfo mi = new PatientMedicalInfo("p5", "Male", "O+", "180 cm", "75 kg");
       PatientRecord record = new PatientRecord("p5", ai, mi, "Health Insurance Co.");


       ArrayList<VisitRecord> visits = new ArrayList<>();
       visits.add(new VisitRecord("p5", "2024-01-01"));
       visits.add(new VisitRecord("p5", "2024-02-01"));
       visits.add(new VisitRecord("p5", "2024-03-01"));
       visits.add(new VisitRecord("p5", "2024-04-01"));
       visits.add(new VisitRecord("p5", "2024-05-01"));
       visits.add(new VisitRecord("p5", "2024-06-01"));
       visits.add(new VisitRecord("p5", "2024-07-01"));
       record.setVisits(visits);
       PatientFileManager fileManager = new PatientFileManager();
       fileManager.postPatientRecord(record);


       ViewPatientRecordCommand command = new ViewPatientRecordCommand(fileManager, new Scanner(System.in));
       command.execute();


      


   }
}

