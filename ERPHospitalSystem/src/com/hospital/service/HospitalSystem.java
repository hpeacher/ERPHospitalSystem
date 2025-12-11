package com.hospital.service;

import com.hospital.command.NurseAssignmentStrategy;
import com.hospital.command.impl.*;
import com.hospital.model.*;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.repository.ILabOrderRepository;
import com.hospital.repository.impl.InventoryRepository;
import com.hospital.repository.impl.InvoiceFileManager;
import com.hospital.repository.impl.MedicationOrderRepository;
import com.hospital.repository.impl.LabOrderRepository;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.repository.impl.TransactionRepository;
import java.util.Scanner;

public class HospitalSystem {
    private static HospitalSystem instance;
    private Hospital hospital;
    private DischargeManager dischargeManager;
    private DoctorManager doctorManager;
    private EmployeeViewer employeeViewer;
    private static int patientIndexCounter = 0;
    private boolean running = true;
    final static int DEFAULT_CAPACITY = 100;

    private HospitalSystem(int capacity) {
        this.hospital = new Hospital(capacity);
        this.doctorManager = new DoctorManager();
        addStaff();
        this.employeeViewer = new EmployeeViewer(hospital, doctorManager);
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
        AppointmentScheduler appointmentScheduler = new AppointmentScheduler(patientFileManager);
        ILabOrderRepository labOrderRepository = new LabOrderRepository();
        ILabOrderService labOrderService = new LabOrderService(labOrderRepository, patientFileManager);
        system.dischargeManager = new DischargeManager(system.hospital, patientFileManager, billingProcessor);
        System.out.println("Hospital System initialized with capacity: " + DEFAULT_CAPACITY);
        DisplayContainer display = new DisplayContainer();
        NurseAssignmentStrategy nurseAssignmentStrategy = new LeastAssignedStrategy(system.hospital);
        HospitalController hospitalController = new HospitalController(system.hospital, nurseAssignmentStrategy);
        FollowUpAnalyzer analyzer = new FollowUpAnalyzer();
        InventoryRepository inventoryRepository = new InventoryRepository(
                "./ERPHospitalSystem/src/com/hospital/repository");
        TransactionRepository transactionRepository = new TransactionRepository(
                "./ERPHospitalSystem/src/com/hospital/repository");
        MedicationOrderRepository orderRepository = new MedicationOrderRepository(
                "./ERPHospitalSystem/src/com/hospital/repository");
        InventoryService inventoryService = new InventoryService(inventoryRepository, transactionRepository, orderRepository);
        UtilizationReportService utilizationReportService =
                new UtilizationReportService(system.getHospital(), system.doctorManager);
        Scanner sc = new Scanner(System.in);

        display.registerCommand(new AdmitPatientCommand(hospitalController, sc));
        display.registerCommand(new ExitCommand(() -> system.stopRunning()));
        display.registerCommand(new HelpCommand(display));
        // display.registerCommand(new DiagnosisCommand());
        display.registerCommand(new DischargePatientCommand(system.dischargeManager, sc));
        display.registerCommand(new ScheduleAppointmentCommand(appointmentScheduler, sc));
        display.registerCommand(new RecommendFollowUpCommand(patientFileManager, analyzer, appointmentScheduler, sc));
        display.registerCommand(new ManageAppointmentsCommand(
                patientFileManager,
                appointmentScheduler,
                sc));
        display.registerCommand(new ManageDoctorsCommand(system.doctorManager, sc));
        display.registerCommand(new ViewEmployeesCommand(system.employeeViewer, sc));
        display.registerCommand(new ProcessInvoiceCommand(billingProcessor, sc, invoiceFileManager));
        display.registerCommand(new InventoryCommand(sc, inventoryService));
        display.registerCommand(new DeletePatientRecordCommand(patientFileManager, sc));
        display.registerCommand(new ViewPatientRecordCommand(patientFileManager, sc));
        display.registerCommand(
                new UtilizationReportCommand(
                        utilizationReportService,
                        patientFileManager, sc
                )
        );
        display.registerCommand(new ProcessLabOrderCommand(labOrderService, sc));

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
    }

    private void addStaff() {
        hospital.addNurse(new Nurse("N001", "Alice", hospital));
        hospital.addNurse(new Nurse("N002", "Bob", hospital));
        hospital.addNurse(new Nurse("N003", "Charlie", hospital));

        doctorManager.addDoctor(new Doctor("D001", "Dr. Smith", "Cardiology", "Cardiology", "555-1001"));
        doctorManager.addDoctor(new Doctor("D002", "Dr. Johnson", "Pediatrics", "Pediatrics", "555-1002"));
        doctorManager.addDoctor(new Doctor("D003", "Dr. Williams", "Neurology", "Neurology", "555-1003"));
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

    /**
     * Demo method to showcase the manageDoctors and viewEmployees use cases
     * This demonstrates what the output should look like when the professor runs the system
     */
    public static void demonstrateUseCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DEMONSTRATION OF USE CASES: manageDoctors and viewEmployees");
        System.out.println("=".repeat(80));

        HospitalSystem system = HospitalSystem.getInstance();
        DoctorManager doctorManager = system.doctorManager;
        EmployeeViewer employeeViewer = system.employeeViewer;

        // ============================================
        // USE CASE 1: VIEW EMPLOYEES
        // ============================================
        System.out.println("\n### USE CASE 1: VIEW EMPLOYEES ###\n");

        System.out.println("1A. View All Employees:");
        System.out.println("-".repeat(60));
        java.util.List<Employee> allEmployees = employeeViewer.getAllEmployees();
        System.out.println("Total Employees: " + allEmployees.size());
        for (Employee emp : allEmployees) {
            System.out.println("  - " + emp);
        }

        System.out.println("\n1B. Search Employees by Role (Doctor):");
        System.out.println("-".repeat(60));
        java.util.List<Employee> doctors = employeeViewer.getEmployeesByRole("Doctor");
        System.out.println("Total Doctors: " + doctors.size());
        for (Employee emp : doctors) {
            System.out.println("  - " + emp);
        }

        System.out.println("\n1C. Search Employees by Role (Nurse):");
        System.out.println("-".repeat(60));
        java.util.List<Employee> nurses = employeeViewer.getEmployeesByRole("Nurse");
        System.out.println("Total Nurses: " + nurses.size());
        for (Employee emp : nurses) {
            System.out.println("  - " + emp);
        }

        System.out.println("\n1D. Search Employee by ID (D001):");
        System.out.println("-".repeat(60));
        Employee emp = employeeViewer.getEmployeeById("D001");
        if (emp != null) {
            System.out.println("  Found: " + emp);
        } else {
            System.out.println("  Not found");
        }

        System.out.println("\n1E. Search Employees by Name (Dr):");
        System.out.println("-".repeat(60));
        java.util.List<Employee> matching = employeeViewer.searchEmployeeByName("Dr");
        System.out.println("Total matches: " + matching.size());
        for (Employee e : matching) {
            System.out.println("  - " + e);
        }

        // ============================================
        // USE CASE 2: MANAGE DOCTORS
        // ============================================
        System.out.println("\n### USE CASE 2: MANAGE DOCTORS ###\n");

        System.out.println("2A. View All Doctors (Initial):");
        System.out.println("-".repeat(60));
        java.util.List<Doctor> allDoctors = doctorManager.getAllDoctors();
        System.out.println("Total Doctors: " + allDoctors.size());
        for (Doctor doc : allDoctors) {
            System.out.println("  - " + doc);
        }

        System.out.println("\n2B. Add a New Doctor:");
        System.out.println("-".repeat(60));
        Doctor newDoctor = new Doctor("D004", "Dr. Brown", "Orthopedics", "Surgery", "555-1004");
        doctorManager.addDoctor(newDoctor);
        System.out.println("Expected output: 'Doctor successfully added: Dr. Brown'");

        System.out.println("\n2C. View All Doctors (After Adding):");
        System.out.println("-".repeat(60));
        allDoctors = doctorManager.getAllDoctors();
        System.out.println("Total Doctors: " + allDoctors.size());
        for (Doctor doc : allDoctors) {
            System.out.println("  - " + doc);
        }

        System.out.println("\n2D. Get Doctor by ID (D004):");
        System.out.println("-".repeat(60));
        Doctor doctor = doctorManager.getDoctorById("D004");
        if (doctor != null) {
            System.out.println("  Found: " + doctor);
        } else {
            System.out.println("  Not found");
        }

        System.out.println("\n2E. Attempt to Add Duplicate Doctor ID:");
        System.out.println("-".repeat(60));
        Doctor duplicateDoctor = new Doctor("D004", "Dr. Duplicate", "Dermatology", "Skin", "555-9999");
        doctorManager.addDoctor(duplicateDoctor);
        System.out.println("Expected output: 'Error: Doctor ID D004 already exists.'");

        System.out.println("\n2F. Add Another New Doctor:");
        System.out.println("-".repeat(60));
        Doctor anotherDoctor = new Doctor("D005", "Dr. Green", "Psychiatry", "Mental Health", "555-1005");
        doctorManager.addDoctor(anotherDoctor);
        System.out.println("Expected output: 'Doctor successfully added: Dr. Green'");

        System.out.println("\n2G. View All Doctors (Final):");
        System.out.println("-".repeat(60));
        allDoctors = doctorManager.getAllDoctors();
        System.out.println("Total Doctors: " + allDoctors.size());
        for (Doctor doc : allDoctors) {
            System.out.println("  - " + doc);
        }

        System.out.println("\n2H. Remove Doctor (D005 - no patients):");
        System.out.println("-".repeat(60));
        boolean removed = doctorManager.removeDoctor("D005");
        if (removed) {
            System.out.println("Expected output: 'Doctor D005 successfully removed.'");
        } else {
            System.out.println("Failed to remove doctor");
        }

        System.out.println("\n2I. View All Doctors (After Removal):");
        System.out.println("-".repeat(60));
        allDoctors = doctorManager.getAllDoctors();
        System.out.println("Total Doctors: " + allDoctors.size());
        for (Doctor doc : allDoctors) {
            System.out.println("  - " + doc);
        }

        System.out.println("\n2J. Attempt to Remove Non-Existent Doctor:");
        System.out.println("-".repeat(60));
        removed = doctorManager.removeDoctor("D999");
        if (!removed) {
            System.out.println("Expected output: 'Error: Doctor ID D999 not found.'");
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("END OF DEMONSTRATION");
        System.out.println("=".repeat(80) + "\n");
    }
}
