package com.hospital.model;

public class DoctorWorkloadEntry {
    private final Doctor doctor;
    private final int appointmentCount;
    private final boolean overloaded;

    public DoctorWorkloadEntry(Doctor doctor, int appointmentCount, boolean overloaded) {
        this.doctor = doctor;
        this.appointmentCount = appointmentCount;
        this.overloaded = overloaded;
    }

    public Doctor getDoctor()        { return doctor; }
    public int getAppointmentCount() { return appointmentCount; }
    public boolean isOverloaded()    { return overloaded; }
}
