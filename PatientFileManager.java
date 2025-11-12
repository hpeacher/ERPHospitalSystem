import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

interface IPatientFileManager {
    boolean postPatientRecord(PatientRecord record);

    PatientRecord getPatientRecord(String patientId);

    boolean deletePatientRecord(String patientId);
}

public class PatientFileManager implements IPatientFileManager {

    public PatientFileManager() {
        // Constructor implementation if needed
    }

    @Override
    public boolean postPatientRecord(PatientRecord record) {
        // Implementation for posting a patient record
        String folderName = "patient_directory";
        File folder = new File(folderName);

        // Create folder if it doesn't exist
        if (!folder.exists()) {
            folder.mkdir();
        }

        String filename = folderName + File.separator + record.getPatientId() + ".json";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(JsonSerializer.toJson(record));
            System.out.println("PatientRecord saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public PatientRecord getPatientRecord(String patientId) {
        // Implementation for getting a patient record
        File folder = new File("patient_directory");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory not found.");
            return null;
        }

        File target = new File(folder, patientId + ".json");
        if (target.exists()) {
            System.out.println("Found file: " + target.getName());
            return JsonSerializer.fromJson(target, PatientRecord.class);
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
}
