import java.util.List;

public class AppointmentScheduler {
    private PatientFileManager fileManager;

    public AppointmentScheduler(PatientFileManager fileManager) {
        this.fileManager = fileManager;
    }

    public Appointment scheduleAppointment(String patientId,
                                           String doctorName,
                                           String date,
                                           String time) throws Exception {

        // 1. Load patient
        PatientRecord record = fileManager.getPatientRecord(patientId);
        if (record == null) {
            throw new Exception("Patient not found: " + patientId);
        }

        // 2. Check for conflicting appointment times
        List<Appointment> existing = record.getAppointments();
        for (Appointment ap : existing) {
            if (ap.getDate().equals(date) && ap.getTime().equals(time)) {
                throw new Exception("Patient already has an appointment at this time.");
            }
        }

        // 3. Generate new appointment ID
        int newId = existing.size() + 1;

        Appointment appt = new Appointment(newId, patientId, doctorName, date, time);

        // 4. Add to patient record
        record.addAppointment(appt);

        // 5. Save updated file
        fileManager.postPatientRecord(record);

        return appt;
    }
}
