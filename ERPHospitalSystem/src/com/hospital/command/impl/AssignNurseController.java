package com.hospital.command.impl;

import com.hospital.command.IAssignNurseController;
import com.hospital.service.IAssignNurseService;

public class AssignNurseController implements IAssignNurseController {

    private final IAssignNurseService service;

    public AssignNurseController(IAssignNurseService service) {
        this.service = service;
    }

    @Override
    public String assignNurseToPatient(String patientId, String nurseId) {
        return service.assignNurseToPatient(patientId, nurseId);
    }
}
