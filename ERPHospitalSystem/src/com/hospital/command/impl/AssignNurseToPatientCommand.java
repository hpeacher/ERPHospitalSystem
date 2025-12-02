package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.command.IAssignNurseController;

public class AssignNurseToPatientCommand implements ICommand {

    private final IAssignNurseController controller;
    private final String patientId;
    private final String nurseId;

    public AssignNurseToPatientCommand(IAssignNurseController controller,
                                       String patientId,
                                       String nurseId) {
        this.controller = controller;
        this.patientId = patientId;
        this.nurseId = nurseId;
    }

    @Override
    public String getToken() {
        return "assign_nurse";
    }

    @Override
    public void execute() {
        String result = controller.assignNurseToPatient(patientId, nurseId);
        System.out.println(result);
    }
}


