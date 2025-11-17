import java.util.ArrayList;

class Doctor extends Employee {
    private String doctorId;  // Changed to String for consistency
    private String specialty;
    private ArrayList<String> assignedPatients;

    public Doctor(String doctorId, String name, String specialty, String department, String contactInfo) {
        super(doctorId, name, "Doctor", department, contactInfo);
        this.doctorId = doctorId;
        this.specialty = specialty;
        this.assignedPatients = new ArrayList<String>();
    }

    // Legacy constructor for backward compatibility
    public Doctor(int doctorId, String name, String specialty) {
        super(String.valueOf(doctorId), name, "Doctor", "General", "N/A");
        this.doctorId = String.valueOf(doctorId);
        this.specialty = specialty;
        this.assignedPatients = new ArrayList<String>();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void diagnose(PatientRecord patient, String description, String prescription, IDiagnosisService service) {
        service.saveDiagnosis(patient.getPatientId(), description, prescription);
    }

    // Methods for managing assigned patients
    public void assignPatient(String patientId) {
        if (!assignedPatients.contains(patientId)) {
            assignedPatients.add(patientId);
        }
    }

    public void unassignPatient(String patientId) {
        assignedPatients.remove(patientId);
    }

    public boolean hasActivePatients() {
        return !assignedPatients.isEmpty();
    }

    public ArrayList<String> getAssignedPatients() {
        return assignedPatients;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId + " | Name: " + name + " | Specialty: " + specialty +
               " | Department: " + department + " | Contact: " + contactInfo +
               " | Active Patients: " + assignedPatients.size();
    }
}
