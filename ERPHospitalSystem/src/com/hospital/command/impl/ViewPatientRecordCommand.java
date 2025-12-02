package com.hospital.command.impl;


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




       System.out.println("\nWhat would you like to do?");
           System.out.println("1. View Administrative Info");
           System.out.println("2. View Medical Info");
           System.out.println("3. View Visit History");
           System.out.println("4. Edit Administrative Info");
           System.out.println("5. Edit Medical Info");
           System.out.println("6. Edit Visit Records");
           System.out.println("7. View FULL patient record");
           String choice = sc.nextLine();


           switch (choice) {
               case "1": viewAdministrative(record); break;
               case "2": viewMedical(record); break;
               case "3": viewVisits(record); break;
               case "4": editAdministrative(record); break;
               case "5": editMedical(record); break;
               case "6": editVisits(record); break;
               case "7": viewFull(record); break;


               default:
                   System.out.println("Invalid choice.");
           }
   }
   private void viewAdministrative(PatientRecord record) {
       System.out.println("\n--- Administrative Info ---");
       PatientAdministrativeInfo info = record.getAdministrativeInfo();


       if (info == null) {
           System.out.println("No administrative info found.");
           return;
       }


       System.out.println("Name: " + info.getName());
       System.out.println("DOB: " + info.getDateOfBirth());
       System.out.println("Address: " + info.getAddress());
       System.out.println("Phone: " + info.getPhoneNumber());
       System.out.println("Insurance: " + record.getInsurance());
   }
   private void viewMedical(PatientRecord record) {
       System.out.println("\n--- Medical Info ---");
       PatientMedicalInfo med = record.getMedicalInfo();


       if (med == null) {
           System.out.println("No medical info found.");
           return;
       }


       System.out.println("Name: " + record.getAdminInfo().getName());
       System.out.println("Gender: " + med.getGender());
       System.out.println("Height: " + med.getHeight());
       System.out.println("Weight: " + med.getWeight());
       System.out.println("Blood Type: " + med.getBloodType());
   }


   private void viewVisits(PatientRecord record) {
       System.out.println("\n--- Visit History ---");


       if (record.getVisitHistory() == null || record.getVisitHistory().isEmpty()) {
           System.out.println("No visits.");
           return;
       }


       int i = 1;
       for (VisitRecord visit : record.getVisitHistory()) {
           System.out.println("\nVisit #" + i++);

           System.out.println("Visit Id: " + visit.getId());
       }
   }


   private void viewFull(PatientRecord record) {
       viewAdministrative(record);
       System.out.println();
       viewMedical(record);
       System.out.println();
       viewVisits(record);
   }


   private void editAdministrative(PatientRecord record) {
       PatientAdministrativeInfo admin = record.getAdministrativeInfo();


   if (admin == null) {
       admin = new PatientAdministrativeInfo();
       record.setAdministrativeInfo(admin);
   }


   System.out.println("\n--- Edit Administrative Info ---");
   System.out.println("Leave any field blank to keep its current value.\n");


   // Full Name
   System.out.print("Full Name (" + admin.getName() + "): ");
   String name = sc.nextLine();
   if (!name.isEmpty()) admin.setName(name);


   // DOB
   System.out.print("Date of Birth (" + admin.getDateOfBirth() + "): ");
   String dob = sc.nextLine();
   if (!dob.isEmpty()) admin.setDateOfBirth(dob);


   // Address
   System.out.print("Address (" + admin.getAddress() + "): ");
   String addr = sc.nextLine();
   if (!addr.isEmpty()) admin.setAddress(addr);


   // Phone
   System.out.print("Phone Number (" + admin.getPhoneNumber() + "): ");
   String phone = sc.nextLine();
   if (!phone.isEmpty()) admin.setPhoneNumber(phone);


   // Insurance
   System.out.print("Insurance Provider (" + record.getInsurance() + "): ");
   String ins = sc.nextLine();
   if (!ins.isEmpty()) record.setInsurance(ins);


   fileManager.postPatientRecord(record);
   System.out.println("Administrative info updated successfully!");
   }


   private void editMedical(PatientRecord record) {
   PatientMedicalInfo med = record.getMedicalInfo();


   if (med == null) {
       med = new PatientMedicalInfo();
       record.setMedicalInfo(med);
   }


   System.out.println("\n--- Edit Medical Info ---");
   System.out.println("Leave blank to keep current value.\n");


   System.out.print("Height (" + med.getHeight() + "): ");
   String allergies = sc.nextLine();
   if (!allergies.isEmpty()) med.setHeight(allergies);


   System.out.print("Weight (" + med.getWeight() + "): ");
   String cond = sc.nextLine();
   if (!cond.isEmpty()) med.setWeight(cond);


   System.out.print("Gender (" + med.getGender() + "): ");
   String meds = sc.nextLine();
   if (!meds.isEmpty()) med.setGender(meds);


   System.out.print("Blood Type (" + med.getBloodType() + "): ");
   String blood = sc.nextLine();
   if (!blood.isEmpty()) med.setBloodType(blood);


   fileManager.postPatientRecord(record);
   System.out.println("Medical info updated!");
}


private void editVisits(PatientRecord record) {
       viewVisits(record);


       System.out.print("Which visit to edit (#): ");
       int index = Integer.parseInt(sc.nextLine());


       VisitRecord visit = record.getVisitRecord(index);
       if (visit == null) {
           System.out.println("Invalid visit number.");
           return;
       }




       fileManager.postPatientRecord(record);
       System.out.println("Visit updated.");
   }


}
