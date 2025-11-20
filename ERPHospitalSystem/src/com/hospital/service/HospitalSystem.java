package com.hospital.service;

import java.util.Scanner;
import com.hospital.command.NurseAssignmentStrategy;
import com.hospital.command.impl.*;
import com.hospital.model.*;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.repository.impl.InvoiceFileManager;
import com.hospital.repository.impl.PatientFileManager;

public class HospitalSystem {
    private static HospitalSystem instance;
    private Hospital hospital;
    private DischargeManager dischargeManager;
    private static int patientIndexCounter = 0;
    private boolean running = true;
    final static int DEFAULT_CAPACITY = 100;

    private HospitalSystem(int capacity) {
        this.hospital = new Hospital(capacity);
        addStaff();
    }

    public static synchronized HospitalSystem getInstance() {
        if (instance == null) {
            instance = new HospitalSystem(DEFAULT_CAPACITY);
        }
        return instance;
    }

    public static void main(String[] args) {
        // Initialize the Hospital System
        HospitalSystem system = HospitalSystem.getInstance();
        PatientFileManager patientFileManager = new PatientFileManager();
        IInvoiceFileManager invoiceFileManager = new InvoiceFileManager();
        BillingProcessor billingProcessor = new BillingProcessor();
        system.dischargeManager = new DischargeManager(system.hospital, billingProcessor, patientFileManager);
        System.out.println("Hospital System initialized with capacity: " + DEFAULT_CAPACITY);
        DisplayContainer display = new DisplayContainer();
        NurseAssignmentStrategy nurseAssignmentStrategy = new LeastAssignedStrategy(system.hospital);
        HospitalController hospitalController = new HospitalController(system.hospital, nurseAssignmentStrategy);
        Scanner sc = new Scanner(System.in);

        display.registerCommand(new AdmitPatientCommand(hospitalController, sc));
        // display.registerCommand(new ScheduleAppointmentCommand());
        display.registerCommand(new ExitCommand(() -> system.stopRunning()));
        display.registerCommand(new HelpCommand(display));
        // display.registerCommand(new DiagnosisCommand());
        display.registerCommand(new DischargePatientCommand(system.dischargeManager, sc));
        display.registerCommand(new ProcessInvoiceCommand(billingProcessor, sc, invoiceFileManager));

        /*
         * Main loop that utilizes the display container to allow user commands.
         */
        while (system.running) {
            display.showDefaultScreen();
            System.out.print("> ");
            String input = sc.nextLine();
            display.acceptUserInput(input);
        }

        sc.close();
        System.exit(0);

        // Schedule Appointment demo
        // Hardcoded patient data
        // patients.put("P123", "John Doe");
        // patients.put("P456", "Jane Smith");
        // patients.put("P789", "Michael Johnson");
        // patients.put("P321", "Emily Davis");
        // patients.put("P654", "David Wilson");
        // System.out.print("Enter Patient ID: ");
        // String id = sc.nextLine();
        // if (!patients.containsKey(id)) {
        // System.out.println("Patient not found. Please register.");
        // sc.close();
        // return;
        // }
        // System.out.println("Patient found: " + patients.get(id));
        // System.out.println("Available doctors: " + doctors);
        // System.out.print("Select doctor: ");
        // String doc = sc.nextLine();
        // if (!doctors.contains(doc)) {
        // System.out.println("Doctor not available.");
        // } else {
        // String confirmation = "A" + (int) (Math.random() * 10000);
        // System.out.println("Appointment booked with " + doc + " (Confirmation #: " +
        // confirmation + ")");
        // }
        // sc.close();

        // IFileStorage storage = new FileStorage("diagnosis_records.txt");
        // IDiagnosisRepository repo = new DiagnosisRepository(storage);
        // IDiagnosisService service = new DiagnosisService(repo);
        // DoctorController controller = new DoctorController(service);

        // PatientAdministrativeInfo adminInfo = new PatientAdministrativeInfo("1",
        // "John Doe", "1985-06-21", "555-1234",
        // "123 Main St");
        // PatientMedicalInfo medicalInfo = new PatientMedicalInfo("1", "M", "O+", "180
        // cm", "75 kg");

        // PatientRecord patient = new PatientRecord("1", adminInfo, medicalInfo,
        // "insuranceDefault");
        // Doctor doctor = new Doctor(101, "Dr. Smith", "Cardiology");

        // controller.requestDiagnose(patient);
        // doctor.diagnose(patient, "Chest pain due to hypertension", "Lisinopril 10mg
        // daily", service);

    }

    private void addStaff() {
        hospital.addNurse(new Nurse("N001", "Alice", hospital));
        hospital.addNurse(new Nurse("N002", "Bob", hospital));
        hospital.addNurse(new Nurse("N003", "Charlie", hospital));
    }

    public Hospital getHospital() {
        return hospital;
    }

    public static synchronized int getNextPatientIndex() {
        return ++patientIndexCounter;
    }

    public void stopRunning() {
        running = false;
    }
}
