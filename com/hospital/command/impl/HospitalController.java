package com.hospital.command.impl;

import com.hospital.model.*;
import com.hospital.repository.IPatientFileManager;
import com.hospital.repository.impl.PatientFileManager;
import com.hospital.service.HospitalSystem;
import com.hospital.command.IHospitalController;

public class HospitalController implements IHospitalController {

    private final HospitalSystem system = HospitalSystem.getInstance();
    private final IPatientFileManager fileMgr = new PatientFileManager();

    @Override
    public String admitPatient(AdmitDTO dto) {
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
            fileMgr.postPatientRecord(record);
        }

        VisitRecord visit = new VisitRecord(record.getPatientId());
        visit.setNotes("Admitted to " + nz(dto.department) + " | Reason: " + nz(dto.reason));

        Invoice invoice = new Invoice(0, 0.0, record.getPatientId(), record.getInsurance());
        visit.setInvoice(invoice);

        record.addVisit(visit);
        fileMgr.postPatientRecord(record);

        return "VISIT-" + visit.getId();
    }

    private static String nz(String s) {
        return (s == null) ? "" : s;
    }
}
