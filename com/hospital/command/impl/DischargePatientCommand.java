package com.hospital.command.impl;

import java.util.Scanner;

import com.hospital.command.ICommand;
import com.hospital.service.DischargeManager;

public class DischargePatientCommand implements ICommand {
    private final DischargeManager ctrl;

    public DischargePatientCommand(DischargeManager ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public String getToken() {
        return "discharge_patient";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient ID to discharge: ");
        String patientId = scanner.nextLine();

        ctrl.initiateDischarge(patientId);
        System.out.println("Patient with ID " + patientId + " has been discharged.");

        scanner.close();
    }
}