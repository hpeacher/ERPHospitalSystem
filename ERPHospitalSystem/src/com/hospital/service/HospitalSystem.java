package com.hospital.service;

import java.util.Scanner;
import com.hospital.command.NurseAssignmentStrategy;
import com.hospital.command.impl.*;
import com.hospital.model.*;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.repository.impl.InventoryRepository;
import com.hospital.repository.impl.InvoiceFileManager;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.repository.impl.TransactionRepository;

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
        system.dischargeManager = new DischargeManager(system.hospital, patientFileManager);
        System.out.println("Hospital System initialized with capacity: " + DEFAULT_CAPACITY);
        DisplayContainer display = new DisplayContainer();
        NurseAssignmentStrategy nurseAssignmentStrategy = new LeastAssignedStrategy(system.hospital);
        HospitalController hospitalController = new HospitalController(system.hospital, nurseAssignmentStrategy);
        FollowUpAnalyzer analyzer = new FollowUpAnalyzer();
        InventoryRepository inventoryRepository = new InventoryRepository(
                "./ERPHospitalSystem/src/com/hospital/repository");
        TransactionRepository transactionRepository = new TransactionRepository(
                "./ERPHospitalSystem/src/com/hospital/repository");
        InventoryService inventoryService = new InventoryService(inventoryRepository, transactionRepository);
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
}
