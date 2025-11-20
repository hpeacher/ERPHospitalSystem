package com.hospital.service;

import com.hospital.model.Hospital;
import com.hospital.model.Nurse;
import com.hospital.repository.INurseRepository;
import com.hospital.service.IAssignNurseService;

public class AssignNurseService implements IAssignNurseService {

    private final Hospital hospital;
    private final INurseRepository nurseRepo;

    public AssignNurseService(Hospital hospital, INurseRepository nurseRepo) {
        this.hospital = hospital;
        this.nurseRepo = nurseRepo;
    }

    @Override
    public String assignNurseToPatient(String patientId, String nurseId) {

        // 1. Patient must be admitted
        if (!hospital.isPatientAdmitted(patientId)) {
            return "Patient not admitted";
        }

        // 2. Nurse must exist
        Nurse nurse = nurseRepo.getNurseById(nurseId);
        if (nurse == null) {
            return "Nurse not found";
        }

        // 3. Check nurse workload limit if necessary
        if (nurse.getAssignedPatients().size() >= 5) {  // example workload rule
            return "Nurse workload exceeded";
        }

        // 4. Assignment
        hospital.assignNurseToPatient(nurseId, patientId);

        return "success";
    }
}

