package com.hospital.command.impl;

import java.util.Comparator;
import java.util.List;

import com.hospital.model.*;
import com.hospital.command.NurseAssignmentStrategy;

public class LeastAssignedStrategy implements NurseAssignmentStrategy {
    private final Hospital hospital;

    public LeastAssignedStrategy(Hospital hospital) {
        this.hospital = hospital;
    }

    @Override
    public Nurse chooseNurse(List<Nurse> nurses, String patientId) {
        return nurses.stream()
                .min(Comparator.comparingInt(n -> hospital.getPatientsAssignedTo(n.getId()).size()))
                .orElse(null);
    }
}