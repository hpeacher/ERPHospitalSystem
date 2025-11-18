package com.hospital.command.impl;

import java.util.Scanner;

import com.hospital.command.ICommand;
import com.hospital.command.IHospitalController;
import com.hospital.model.AdmitDTO;

public class AdmitPatientCommand implements ICommand {
    private final IHospitalController ctrl;

    public AdmitPatientCommand(IHospitalController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public String getToken() {
        return "admit_patient";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        AdmitDTO dto = new AdmitDTO();

        System.out.print("Enter patient ID: ");
        dto.patientId = scanner.nextLine();

        System.out.print("Enter name: ");
        dto.name = scanner.nextLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        dto.dob = scanner.nextLine();

        System.out.print("Enter phone number: ");
        dto.phone = scanner.nextLine();

        System.out.print("Enter address: ");
        dto.address = scanner.nextLine();

        System.out.print("Enter department: ");
        dto.department = scanner.nextLine();

        System.out.print("Enter reason for visit: ");
        dto.reason = scanner.nextLine();

        String visitId = ctrl.admitPatient(dto);
        System.out.println("Patient admitted with visit ID: " + visitId);

        scanner.close();
    }
}
