package com.hospital.command.impl;

import java.util.Scanner;

import com.hospital.command.ICommand;
import com.hospital.service.DischargeManager;

public class DischargePatientCommand implements ICommand {
    private final DischargeManager ctrl;
    private final Scanner scanner;

    public DischargePatientCommand(DischargeManager ctrl, Scanner scanner) {
        this.ctrl = ctrl;
        this.scanner = scanner;
    }

    @Override
    public String getToken() {
        return "discharge_patient";
    }

    @Override
    public void execute() {
        System.out.print("Enter patient ID to discharge: ");
        String patientId = scanner.nextLine();

        ctrl.initiateDischarge(patientId);
    }
}