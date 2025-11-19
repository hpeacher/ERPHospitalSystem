import java.util.ArrayList;
import java.util.List;

public class PatientRecord {
    private String patientId;
    private PatientAdministrativeInfo administrativeInfo;
    private PatientMedicalInfo medicalInfo;
    private ArrayList<VisitRecord> visits;
    private String insurance;
    private ArrayList<Appointment> appointments;
    // Constructor
    public PatientRecord(String patientId, PatientAdministrativeInfo administrativeInfo,
            PatientMedicalInfo medicalInfo, String insurance) {
        this.patientId = patientId;
        this.administrativeInfo = administrativeInfo;
        this.medicalInfo = medicalInfo;
        this.visits = new ArrayList<VisitRecord>();
        this.insurance = insurance;
        this.appointments = new ArrayList<>();
    }

    public PatientRecord() {
        this.visits = new ArrayList<VisitRecord>();
        this.appointments = new ArrayList<>();
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public PatientAdministrativeInfo getAdministrativeInfo() {
        return administrativeInfo;
    }

    public PatientMedicalInfo getMedicalInfo() {
        return medicalInfo;
    }

    public ArrayList<VisitRecord> getVisits() {
        return visits;
    }

    public String getInsurance() {
        return insurance;
    }

    // Setters
    public void setAdministrativeInfo(PatientAdministrativeInfo administrativeInfo) {
        this.administrativeInfo = administrativeInfo;
    }

    public void setMedicalInfo(PatientMedicalInfo medicalInfo) {
        this.medicalInfo = medicalInfo;
    }

    public String getMedicalHistory() {
        return "Retrieving medical history for: " + administrativeInfo.getName();
    }

    public void addVisit(VisitRecord visit) {
        this.visits.add(visit);
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
    

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }
}
