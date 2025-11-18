package com.hospital.command;

import java.util.List;

import com.hospital.model.Nurse;

public interface NurseAssignmentStrategy {
    Nurse chooseNurse(List<Nurse> nurses, String patientId);
}