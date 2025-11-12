public interface IDiagnosisRepository {
    void insertDiagnosis(String patientId, Diagnosis diagnosis);
}

class DiagnosisRepository implements IDiagnosisRepository {
    private IFileStorage fileStorage;
    private static int counter = 1;

    public DiagnosisRepository(IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void insertDiagnosis(String patientId, Diagnosis diagnosis) {
        String record = "PatientID:" + patientId + "|" + diagnosis.toFileString();
        fileStorage.writeToFile(record);
    }
}
