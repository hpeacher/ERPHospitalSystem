package com.hospital.repository;

import com.hospital.model.Nurse;
import java.util.List;

public interface INurseRepository {

    Nurse getNurseById(String nurseId);

    List<Nurse> getAllNurses();
}

