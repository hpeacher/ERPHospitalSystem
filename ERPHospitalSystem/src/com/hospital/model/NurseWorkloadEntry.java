package com.hospital.model;

public class NurseWorkloadEntry {
    private final Nurse nurse;
    private final int activePatientCount;

    public NurseWorkloadEntry(Nurse nurse, int activePatientCount) {
        this.nurse = nurse;
        this.activePatientCount = activePatientCount;
    }

    public Nurse getNurse()           { return nurse; }
    public int getActivePatientCount(){ return activePatientCount; }
}
