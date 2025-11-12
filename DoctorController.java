
class DoctorController {
    private IDiagnosisService diagnosisService;

    public DoctorController(IDiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    public void requestDiagnose(PatientRecord patient) {
        System.out.println("Fetching medical history...");
        System.out.println(patient.getMedicalHistory());
    }

    public void submitDiagnosis(String patientId, String description, String prescription) {
        diagnosisService.saveDiagnosis(patientId, description, prescription);
    }
}
