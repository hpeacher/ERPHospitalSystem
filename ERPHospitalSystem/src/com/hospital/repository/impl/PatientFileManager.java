package com.hospital.repository.impl;

import java.io.File;
import java.util.ArrayList;

import com.hospital.model.PatientRecord;
import com.hospital.repository.IPatientFileManager;
import com.hospital.repository.JsonSerializer;

public class PatientFileManager implements IPatientFileManager {

    public PatientFileManager() {
        // Constructor implementation if needed
    }

    @Override
    public boolean postPatientRecord(PatientRecord record) {
        String folderName = "patient_directory";
        File folder = new File(folderName);

        if (!folder.exists()) {
            folder.mkdir();
        }

        File target = new File(folder, record.getPatientId() + ".json");

        try {
            JsonSerializer.writeToFile(target, record);
            System.out.println("PatientRecord saved to " + target.getAbsolutePath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PatientRecord getPatientRecord(String patientId) {
        File folder = new File("patient_directory");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory not found.");
            return null;
        }

        File target = new File(folder, patientId + ".json");
        if (target.exists()) {
            System.out.println("Found file: " + target.getName());

            // UPDATED: use Gson readFromFile
            return JsonSerializer.readFromFile(target, PatientRecord.class);
        } else {
            System.out.println("No file found for patientId: " + patientId);
            return null;
        }
    }

    @Override
    public boolean deletePatientRecord(String patientId) {
        // Implementation for deleting a patient record
        File folder = new File("patient_directory");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory not found.");
            return false;
        }

        File target = new File(folder, patientId + ".json");
        if (target.exists()) {
            if (target.delete()) {
                System.out.println("Deleted file: " + target.getName());
            } else {
                System.out.println("Failed to delete file: " + target.getName());
                return false;
            }
        } else {
            System.out.println("No file found for patientId: " + patientId);
            return false;
        }
        return true;
    }
    public static ArrayList<PatientRecord> getAllPatientRecords() {
        ArrayList<PatientRecord> records = new ArrayList<>();

        File folder = new File("patient_directory");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory not found.");
            return records; // return empty list
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return records;
        }

        for (File file : files) {
            try {
                PatientRecord record = JsonSerializer.readFromFile(file, PatientRecord.class);
                if (record != null) {
                    records.add(record);
                }
            } catch (Exception e) {
                System.out.println("Failed to read file: " + file.getName());
            }
        }

        return records;
    }
}
