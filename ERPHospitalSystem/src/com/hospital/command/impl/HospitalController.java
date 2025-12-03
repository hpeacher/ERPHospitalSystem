package com.hospital.command.impl;

import com.hospital.command.IHospitalController;
import com.hospital.command.NurseAssignmentStrategy;
import com.hospital.model.*;
import com.hospital.repository.IPatientFileManager;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.service.HospitalSystem;

public class HospitalController implements IHospitalController {

    private final HospitalSystem system = HospitalSystem.getInstance();
    private final IPatientFileManager fileMgr = new PatientFileManager();
    private final Hospital hospital;
    private final NurseAssignmentStrategy nurseAssignmentStrategy;

    public HospitalController(Hospital hospital, NurseAssignmentStrategy strategy) {
        this.hospital = hospital;
        this.nurseAssignmentStrategy = strategy;
    }

    @Override
    public String admitPatient(AdmitDTO dto) {
        if (!hospital.canAdmitPatient())
            return "failure";

        PatientRecord record = null;
        if (dto.patientId != null && !dto.patientId.isEmpty()) {
            record = fileMgr.getPatientRecord(dto.patientId);
        }

        if (record == null) {
            String newPid = (dto.patientId != null && !dto.patientId.isEmpty())
                    ? dto.patientId
                    : "P" + HospitalSystem.getNextPatientIndex();

            PatientAdministrativeInfo admin = new PatientAdministrativeInfo(
                    newPid, nz(dto.name), nz(dto.dob), nz(dto.phone), nz(dto.address));
            PatientMedicalInfo med = new PatientMedicalInfo(newPid, "", "", "", "");
            record = new PatientRecord(newPid, admin, med, "Default insurance");
            int nextVisitId = record.getVisits().size() + 1;

            VisitRecord visit = new VisitRecord(record.getPatientId(), nextVisitId);
            visit.setVitals("");
            visit.setNotes(dto.reason);
            visit.setDischargeChecklist(new DischargeChecklist());

            record.addVisit(visit);
            fileMgr.postPatientRecord(record);
        }

        hospital.admitPatient(dto.patientId);

        Nurse nurse = nurseAssignmentStrategy.chooseNurse(hospital.getNurses(), dto.patientId);
        if (nurse != null) {
            hospital.assignNurseToPatient(nurse.getId(), dto.patientId);
        }

        return "success";
    }

    public boolean dischargePatient(String patientId) {
        if (!hospital.isPatientAdmitted(patientId)) {
            System.out.println("Patient not found.");
            return false;
        }

        hospital.dischargePatient(patientId);
        System.out.println("Patient " + patientId + " has been discharged.");
        return true;
    }

    public boolean isPatientInSystem(String patientId) {
        PatientRecord record = null;
        if (patientId != null && !patientId.isEmpty()) {
            record = fileMgr.getPatientRecord(patientId);
        }
        return record != null;
    }

    private static String nz(String s) {
        return (s == null) ? "" : s;
    }
}
