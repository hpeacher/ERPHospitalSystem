package com.hospital.model;

import java.util.List;

public class UtilizationReport {

    private final int totalCapacity;
    private final int currentOccupancy;
    private final double occupancyRate;

    private final List<NurseWorkloadEntry> nurseWorkloads;
    private final List<DoctorWorkloadEntry> doctorWorkloads;

    private final List<String> warnings;
    private final List<String> notes;

    public UtilizationReport(int totalCapacity,
                             int currentOccupancy,
                             double occupancyRate,
                             List<NurseWorkloadEntry> nurseWorkloads,
                             List<DoctorWorkloadEntry> doctorWorkloads,
                             List<String> warnings,
                             List<String> notes) {
        this.totalCapacity = totalCapacity;
        this.currentOccupancy = currentOccupancy;
        this.occupancyRate = occupancyRate;
        this.nurseWorkloads = nurseWorkloads;
        this.doctorWorkloads = doctorWorkloads;
        this.warnings = warnings;
        this.notes = notes;
    }

    public int getTotalCapacity()        { return totalCapacity; }
    public int getCurrentOccupancy()     { return currentOccupancy; }
    public double getOccupancyRate()     { return occupancyRate; }
    public List<NurseWorkloadEntry> getNurseWorkloads()   { return nurseWorkloads; }
    public List<DoctorWorkloadEntry> getDoctorWorkloads() { return doctorWorkloads; }
    public List<String> getWarnings()    { return warnings; }
    public List<String> getNotes()       { return notes; }
}