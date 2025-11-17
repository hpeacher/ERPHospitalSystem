import java.util.ArrayList;
import java.util.Scanner;

public class DoctorManagerController {
    private IDoctorManager doctorManager;
    private Scanner scanner;

    public DoctorManagerController(IDoctorManager doctorManager) {
        this.doctorManager = doctorManager;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n=== Manage Doctors ===");
        System.out.println("1. Add Doctor");
        System.out.println("2. Remove Doctor");
        System.out.println("3. View All Doctors");
        System.out.println("4. Return to Main Menu");
        System.out.print("Select an option: ");
    }

    public void handleMenu() {
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    requestAddDoctor();
                    break;
                case "2":
                    requestRemoveDoctor();
                    break;
                case "3":
                    requestViewAllDoctors();
                    break;
                case "4":
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public boolean requestAddDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        
        System.out.print("Enter Doctor ID: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("Error: Doctor ID cannot be empty.");
            return false;
        }

        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Doctor Name cannot be empty.");
            return false;
        }

        System.out.print("Enter Specialty: ");
        String specialty = scanner.nextLine().trim();
        
        if (specialty.isEmpty()) {
            System.out.println("Error: Specialty cannot be empty.");
            return false;
        }

        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();

        System.out.print("Enter Contact Info: ");
        String contactInfo = scanner.nextLine().trim();

        Doctor newDoctor = new Doctor(id, name, specialty, department, contactInfo);
        return doctorManager.addDoctor(newDoctor);
    }

    public boolean requestRemoveDoctor() {
        System.out.println("\n--- Remove Doctor ---");
        
        System.out.print("Enter Doctor ID to remove: ");
        String doctorId = scanner.nextLine().trim();
        
        if (doctorId.isEmpty()) {
            System.out.println("Error: Doctor ID cannot be empty.");
            return false;
        }

        return doctorManager.removeDoctor(doctorId);
    }

    public void requestViewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        ArrayList<Doctor> doctors = doctorManager.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors in the system.");
            return;
        }

        System.out.println("Total Doctors: " + doctors.size());
        System.out.println("-------------------------------------------------------");
        for (Doctor doctor : doctors) {
            System.out.println(doctor.toString());
        }
        System.out.println("-------------------------------------------------------");
    }

    // Programmatic methods for testing without user input
    public boolean requestAddDoctor(String id, String name, String specialty, String department, String contact) {
        Doctor newDoctor = new Doctor(id, name, specialty, department, contact);
        return doctorManager.addDoctor(newDoctor);
    }

    public boolean requestRemoveDoctor(String doctorId) {
        return doctorManager.removeDoctor(doctorId);
    }

    public ArrayList<Doctor> requestViewAllDoctors(boolean silent) {
        return doctorManager.getAllDoctors();
    }
}
