public class DischargeManager {
    private static DischargeManager instance;

    private BillingProcessor billingProcessor;
    private IPatientFileManager patientFileManager;
    private HospitalSystem hospitalSystem;
    private Hospital hospital;

    // Private constructor prevents external instantiation
    private DischargeManager() {
        billingProcessor = new BillingProcessor();
        patientFileManager = new PatientFileManager();
    }

    // Public method to access the single instance
    public static DischargeManager getInstance() {
        if (instance == null) {
            instance = new DischargeManager();
        }
        return instance;
    }

    public void setHospitalSystem(HospitalSystem hospitalSystem) {
        this.hospitalSystem = hospitalSystem;
        this.hospital = hospitalSystem.getHospital();
    }

    public HospitalSystem getHospitalSystem() {
        return hospitalSystem;
    }

    public void initiateDischarge(String patientId) {
        // Implementation here
        Nurse nurse = hospital.findNurseByPatientId(patientId);
        nurse.notifyDischargeInitiated(patientId);
    }

    public void checklistCompleted(VisitRecord visitRecord) {
        PatientRecord patientRecord = billingProcessor.startBillingProcess(visitRecord);
        if (patientRecord != null) {
            // Implementation for generating discharge summary
            patientFileManager.postPatientRecord(patientRecord);
            System.out.println("Discharge summary generated for Patient ID: " + patientRecord.getPatientId());
        }
    }
}