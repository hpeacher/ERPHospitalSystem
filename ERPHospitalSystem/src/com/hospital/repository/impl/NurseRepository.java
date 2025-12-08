package com.hospital.repository.impl;

import com.hospital.model.Hospital;
import com.hospital.model.Nurse;
import com.hospital.repository.INurseRepository;

import java.util.List;

public class NurseRepository implements INurseRepository {

    private final Hospital hospital;

    public NurseRepository(Hospital hospital) {
        this.hospital = hospital;
    }

    @Override
    public Nurse getNurseById(String nurseId) {
        return hospital.getNurseById(nurseId);
    }

    @Override
    public List<Nurse> getAllNurses() {
        return hospital.getNurses();
    }
}

