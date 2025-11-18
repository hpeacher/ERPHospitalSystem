package com.hospital.model;

import java.util.List;
import java.util.Objects;

public class Nurse {
    private String nurseId;
    private String name;
    private Hospital hospital;

    public Nurse(String nurseId, String name, Hospital hospital) {
        this.nurseId = nurseId;
        this.name = name;
        this.hospital = hospital;
    }

    public String getId() {
        return nurseId;
    }

    public String getName() {
        return name;
    }

    public List<String> getAssignedPatients() {
        return hospital.getPatientsAssignedTo(nurseId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Nurse))
            return false;
        Nurse nurse = (Nurse) o;
        return Objects.equals(nurseId, nurse.nurseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nurseId);
    }
}
