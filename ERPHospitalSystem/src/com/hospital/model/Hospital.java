package com.hospital.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hospital {
    private int capacity;
    private final List<String> patients = new ArrayList<>();
    private final List<Nurse> nurses = new ArrayList<>();
    private final Map<String, String> patientToNurseMap = new HashMap<>();

    public Hospital(int capacity) {
        this.capacity = capacity;
    }

    public boolean isPatientAdmitted(String patientId) {
        return patients.contains(patientId);
    }

    public boolean canAdmitPatient() {
        return patients.size() < capacity;
    }

    public void admitPatient(String patientId) {
        patients.add(patientId);
    }

    public void dischargePatient(String patientId) {
        patients.remove(patientId);
        patientToNurseMap.remove(patientId);
    }

    public boolean queryPatient(String patientId) {
        if (patients.contains(patientId)) {
            System.out.println("Patient " + patientId + " is admitted in the hospital.");
            return true;
        } else {
            System.out.println("Patient " + patientId + " is not found in the hospital.");
            return false;
        }
    }

    public void assignNurseToPatient(String nurseId, String patientId) {
        patientToNurseMap.put(patientId, nurseId);
    }

    public Nurse getNurseById(String nurseId) {
        return nurses.stream()
                .filter(n -> n.getId().equals(nurseId))
                .findFirst()
                .orElse(null);
    }

    public Nurse findNurseByPatientId(String patientId) {
        return getNurseById(patientToNurseMap.get(patientId));
    }

    public List<String> getPatientsAssignedTo(String nurseId) {
        return patientToNurseMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(nurseId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getPatients() {
        return patients;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void addNurse(Nurse nurse) {
        this.nurses.add(nurse);
    }

    public void removeNurse(Nurse nurse) {
        this.nurses.remove(nurse);
    }
}
