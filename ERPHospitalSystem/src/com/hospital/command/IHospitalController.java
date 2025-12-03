package com.hospital.command;

import com.hospital.model.AdmitDTO;

public interface IHospitalController {
    String admitPatient(AdmitDTO dto);

    boolean dischargePatient(String patientId);

    boolean isPatientInSystem(String patientId);
}