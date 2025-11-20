package com.hospital.service;

import com.hospital.model.Doctor;
import java.util.ArrayList;
import java.util.List;

public class DoctorManager implements IDoctorManager {
    private ArrayList<Doctor> doctors;

    public DoctorManager() {
        this.doctors = new ArrayList<>();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        for (Doctor existingDoctor : doctors) {
            if (existingDoctor.getDoctorId().equals(doctor.getDoctorId())) {
                System.out.println("Error: Doctor ID " + doctor.getDoctorId() + " already exists.");
                return;
            }
        }
        doctors.add(doctor);
        System.out.println("Doctor successfully added: " + doctor.getName());
    }

    @Override
    public boolean removeDoctor(String doctorId) {
        Doctor doctorToRemove = getDoctorById(doctorId);
        if (doctorToRemove == null) {
            System.out.println("Error: Doctor ID " + doctorId + " not found.");
            return false;
        }

        if (doctorToRemove.hasActivePatients()) {
            System.out.println("Error: Cannot remove doctor " + doctorId + ". Doctor has " +
                             doctorToRemove.getAssignedPatients().size() + " active patient(s).");
            return false;
        }

        doctors.remove(doctorToRemove);
        System.out.println("Doctor " + doctorId + " successfully removed.");
        return true;
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }
}
