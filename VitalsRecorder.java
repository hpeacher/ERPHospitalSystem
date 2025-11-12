interface IVitalsRecorder {
    VisitRecord recordVitals(String patientId);
}

public class VitalsRecorder implements IVitalsRecorder {

    public VitalsRecorder() {

    }

    public VisitRecord recordVitals(String patientId) {
        // Implementation for recording vitals
        VisitRecord visitRecord = new VisitRecord(patientId);
        visitRecord.setVitals("Sample Vitals Data for Patient ID: " + patientId);
        return visitRecord;
    }
}
