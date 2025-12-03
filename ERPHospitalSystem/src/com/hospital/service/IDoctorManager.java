package com.hospital.service;

import com.hospital.model.Doctor;
import java.util.List;

public interface IDoctorManager {
    void addDoctor(Doctor doctor);
    boolean removeDoctor(String doctorId);
    Doctor getDoctorById(String doctorId);
    List<Doctor> getAllDoctors();
}
