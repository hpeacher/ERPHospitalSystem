package com.hospital.model;

import java.util.List;
import java.util.Objects;

public class Nurse extends Employee {
    private Hospital hospital;

    public Nurse(String nurseId, String name, Hospital hospital) {
        super(nurseId, name, "Nurse", "Nursing", "N/A");
        this.hospital = hospital;
    }

    public Nurse(String nurseId, String name, String department, String contactInfo, Hospital hospital) {
        super(nurseId, name, "Nurse", department, contactInfo);
        this.hospital = hospital;
    }

    public String getId() {
        return employeeId;
    }

    public List<String> getAssignedPatients() {
        return hospital.getPatientsAssignedTo(employeeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Nurse))
            return false;
        Nurse nurse = (Nurse) o;
        return Objects.equals(employeeId, nurse.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    @Override
    public String toString() {
        return "Nurse ID: " + employeeId + " | Name: " + name + 
               " | Department: " + department + " | Contact: " + contactInfo;
    }
}
