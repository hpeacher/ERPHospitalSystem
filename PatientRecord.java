import java.util.ArrayList;

public class PatientRecord {
    private String patientId;
    private PatientAdministrativeInfo administrativeInfo;
    private PatientMedicalInfo medicalInfo;
    private ArrayList<VisitRecord> visits;
    private String insurance;

    // Constructor
    public PatientRecord(String patientId, PatientAdministrativeInfo administrativeInfo,
            PatientMedicalInfo medicalInfo, String insurance) {
        this.patientId = patientId;
        this.administrativeInfo = administrativeInfo;
        this.medicalInfo = medicalInfo;
        this.visits = new ArrayList<VisitRecord>();
        this.insurance = insurance;
    }

    public PatientRecord() {
        this.visits = new ArrayList<VisitRecord>();
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
}
