package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.PatientRecord;
import com.hospital.repository.impl.PatientFileManager;

import java.util.List;

public class AppointmentScheduler {

    private final PatientFileManager fileManager;

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

        // Ensure appointments list exists
        List<Appointment> existing = record.getAppointments();

        // 2. Check conflicts
        for (Appointment ap : existing) {
            if (ap.getDate().equals(date) && ap.getTime().equals(time)) {
                throw new Exception("Patient already has an appointment at this time.");
            }
        }

        // 3. Generate new ID
        String newId = "APT-" + (existing.size() + 1);

     // 4. Create appointment
     Appointment appt = new Appointment(newId, patientId, doctorName, date, time);

        // 5. Save back to patient
        record.addAppointment(appt);
        fileManager.postPatientRecord(record);

        return appt;
    }
}
