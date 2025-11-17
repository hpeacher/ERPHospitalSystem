import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HospitalSystem {
    private static HospitalSystem instance;
    private Hospital hospital;
    private DischargeManager dischargeManager;
    private DoctorManager doctorManager;
    private EmployeeViewer employeeViewer;
    private static int patientIndexCounter = 0;
    final static int DEFAULT_CAPACITY = 100;

    static Map<String, String> patients = new HashMap<>();
    static List<String> doctors = Arrays.asList("Dr. Smith", "Dr. John", "Dr. Lee", "Dr. Patel", "Dr. Brown"); // Temp
                                                                                                               // doctor
                                                                                                               // list

    private HospitalSystem(int capacity) {
        this.hospital = new Hospital(capacity);
        this.dischargeManager = DischargeManager.getInstance();
        this.dischargeManager.setHospitalSystem(this);
        this.doctorManager = new DoctorManager();
        this.employeeViewer = new EmployeeViewer(hospital, doctorManager);
        addStaff();
        addInitialDoctors();
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
        System.out.println("Hospital System initialized with capacity: " + DEFAULT_CAPACITY);

        // Schedule Appointment demo
        // Hardcoded patient data
        patients.put("P123", "John Doe");
        patients.put("P456", "Jane Smith");
        patients.put("P789", "Michael Johnson");
        patients.put("P321", "Emily Davis");
        patients.put("P654", "David Wilson");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        String id = sc.nextLine();
        if (!patients.containsKey(id)) {
            System.out.println("Patient not found. Please register.");
            sc.close();
            return;
        }
        System.out.println("Patient found: " + patients.get(id));
        System.out.println("Available doctors: " + doctors);
        System.out.print("Select doctor: ");
        String doc = sc.nextLine();
        if (!doctors.contains(doc)) {
            System.out.println("Doctor not available.");
        } else {
            String confirmation = "A" + (int) (Math.random() * 10000);
            System.out.println("Appointment booked with " + doc + " (Confirmation #: " + confirmation + ")");
        }
        sc.close();

        // Tirmidi Mohamed — Admit Patient demo
        HospitalController ctrl = new HospitalController();
        AdmitDTO dto = new AdmitDTO();
        dto.patientId = "P001";
        dto.name = "Jane Doe";
        dto.dob = "2000-01-01";
        dto.phone = "515-555-1212";
        dto.address = "123 Main St";
        dto.department = "ER";
        dto.reason = "Chest pain";
        String visitId = ctrl.admitPatient(dto);
        System.out.println("Admit complete. Visit ID = " + visitId);

        // Example operations
        // Diagnosis demo
        system.getHospital().patientAdmitted("P001");

        IFileStorage storage = new FileStorage("diagnosis_records.txt");
        IDiagnosisRepository repo = new DiagnosisRepository(storage);
        IDiagnosisService service = new DiagnosisService(repo);
        DoctorController controller = new DoctorController(service);

        PatientAdministrativeInfo adminInfo = new PatientAdministrativeInfo("1", "John Doe", "1985-06-21", "555-1234",
                "123 Main St");
        PatientMedicalInfo medicalInfo = new PatientMedicalInfo("1", "M", "O+", "180 cm", "75 kg");

        PatientRecord patient = new PatientRecord("1", adminInfo, medicalInfo, "insuranceDefault");
        Doctor doctor = new Doctor(101, "Dr. Smith", "Cardiology");

        controller.requestDiagnose(patient);
        doctor.diagnose(patient, "Chest pain due to hypertension", "Lisinopril 10mg daily", service);

        // Discharge demo
        system.dischargeManager.initiateDischarge("P001");

        // ========================================
        // TIRMIDI'S ITERATION 2 USE CASES DEMO
        // ========================================
        System.out.println("\n\n========================================");
        System.out.println("ITERATION 2 - TIRMIDI'S USE CASES DEMO");
        System.out.println("========================================\n");

        // Use Case 1: manageDoctors
        System.out.println("=== USE CASE 1: Manage Doctors ===\n");
        
        DoctorManagerController doctorController = new DoctorManagerController(system.getDoctorManager());
        
        // View all initial doctors
        System.out.println("--- Viewing all initial doctors ---");
        doctorController.requestViewAllDoctors();
        
        // Add a new doctor
        System.out.println("\n--- Adding a new doctor ---");
        doctorController.requestAddDoctor("D004", "Dr. Martinez", "Orthopedics", "Orthopedics", "555-0004");
        
        // Try to add duplicate doctor (should fail)
        System.out.println("\n--- Attempting to add duplicate doctor ---");
        doctorController.requestAddDoctor("D004", "Dr. Duplicate", "General", "General", "555-9999");
        
        // View all doctors after addition
        System.out.println("\n--- Viewing all doctors after addition ---");
        doctorController.requestViewAllDoctors();
        
        // Try to remove a doctor without patients
        System.out.println("\n--- Removing doctor without patients ---");
        doctorController.requestRemoveDoctor("D004");
        
        // Assign a patient to a doctor
        Doctor drSmith = system.getDoctorManager().getDoctorById("D001");
        drSmith.assignPatient("P001");
        System.out.println("\n--- Assigned patient P001 to Dr. Smith ---");
        
        // Try to remove a doctor with active patients (should fail)
        System.out.println("\n--- Attempting to remove doctor with active patients ---");
        doctorController.requestRemoveDoctor("D001");
        
        // View final doctor list
        System.out.println("\n--- Final doctor list ---");
        doctorController.requestViewAllDoctors();

        // Use Case 2: viewEmployees
        System.out.println("\n\n=== USE CASE 2: View Employees ===\n");
        
        EmployeeViewerController employeeController = new EmployeeViewerController(system.getEmployeeViewer());
        
        // View all employees
        System.out.println("--- Viewing all employees ---");
        employeeController.requestViewAllEmployees();
        
        // Search by role - Doctors
        System.out.println("\n--- Searching employees by role: Doctor ---");
        ArrayList<Employee> doctorEmployees = employeeController.requestSearchByRole("Doctor");
        System.out.println("Found " + doctorEmployees.size() + " doctors");
        for (Employee emp : doctorEmployees) {
            System.out.println(emp.toString());
        }
        
        // Search by role - Nurses
        System.out.println("\n--- Searching employees by role: Nurse ---");
        ArrayList<Employee> nurseEmployees = employeeController.requestSearchByRole("Nurse");
        System.out.println("Found " + nurseEmployees.size() + " nurses");
        for (Employee emp : nurseEmployees) {
            System.out.println(emp.toString());
        }
        
        // Search by ID
        System.out.println("\n--- Searching employee by ID: D001 ---");
        Employee foundEmployee = employeeController.requestSearchById("D001");
        if (foundEmployee != null) {
            System.out.println("Found: " + foundEmployee.toString());
        } else {
            System.out.println("Employee not found");
        }
        
        // Search by name
        System.out.println("\n--- Searching employees by name: 'Smith' ---");
        ArrayList<Employee> nameMatches = employeeController.requestSearchByName("Smith");
        System.out.println("Found " + nameMatches.size() + " employee(s) matching 'Smith'");
        for (Employee emp : nameMatches) {
            System.out.println(emp.toString());
        }
        
        // Search by name (partial match)
        System.out.println("\n--- Searching employees by name: 'Dr' (partial match) ---");
        ArrayList<Employee> partialMatches = employeeController.requestSearchByName("Dr");
        System.out.println("Found " + partialMatches.size() + " employee(s) matching 'Dr'");
        for (Employee emp : partialMatches) {
            System.out.println(emp.toString());
        }

        System.out.println("\n========================================");
        System.out.println("END OF ITERATION 2 DEMO");
        System.out.println("========================================");
    }

    private void addStaff() {
        hospital.addNurse(new Nurse("N001", "Alice", hospital));
        hospital.addNurse(new Nurse("N002", "Bob", hospital));
        hospital.addNurse(new Nurse("N003", "Charlie", hospital));
    }

    public Hospital getHospital() {
        return hospital;
    }

    public DoctorManager getDoctorManager() {
        return doctorManager;
    }

    public EmployeeViewer getEmployeeViewer() {
        return employeeViewer;
    }

    public static synchronized int getNextPatientIndex() {
        return ++patientIndexCounter;
    }

    private void addInitialDoctors() {
        // Add some initial doctors to the system
        doctorManager.addDoctor(new Doctor("D001", "Dr. Smith", "Cardiology", "Cardiology", "555-0001"));
        doctorManager.addDoctor(new Doctor("D002", "Dr. Johnson", "Neurology", "Neurology", "555-0002"));
        doctorManager.addDoctor(new Doctor("D003", "Dr. Williams", "Pediatrics", "Pediatrics", "555-0003"));
    }
}
