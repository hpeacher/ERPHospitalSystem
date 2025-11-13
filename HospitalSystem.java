import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HospitalSystem {
    private static HospitalSystem instance;
    private Hospital hospital;
    private DischargeManager dischargeManager;
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
        System.out.println("Hospital System initialized with capacity: " + DEFAULT_CAPACITY);

        
        PatientFileManager fileManager = new PatientFileManager();
        AppointmentScheduler scheduler = new AppointmentScheduler(fileManager);

        Scanner sc = new Scanner(System.in);
        System.out.println("\n Schedule Appointment Demo");

        System.out.print("Enter Patient ID: ");
        String pid = sc.nextLine();

        try {
            // Load real patient record from patient_directory/PXXX.json
            PatientRecord record = fileManager.getPatientRecord(pid);

            if (record == null) {
                System.out.println("Patient not found in system. Please admit first.");
            } else {
                //System.out.println("Patient found: " + record.getAdministrativeInfo().getName());

                System.out.print("Enter doctor name: ");
                String doctorName = sc.nextLine();

                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = sc.nextLine();

                System.out.print("Enter time (HH:MM): ");
                String time = sc.nextLine();

                Appointment appt = scheduler.scheduleAppointment(pid, doctorName, date, time);

                System.out.println("Appointment booked!");
                System.out.println("Appointment ID: " + appt.getAppointmentId());
                System.out.println("Doctor: " + appt.getDoctorName());
                System.out.println("Date: " + appt.getDate() + " at " + appt.getTime());
            }
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }

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
}
