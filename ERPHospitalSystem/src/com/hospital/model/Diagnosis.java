package com.hospital.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Diagnosis {
    private int diagnosisId;
    private String description;
    private String prescription;
    private String dateRecorded;

    public Diagnosis(int diagnosisId, String description, String prescription) {
        this.diagnosisId = diagnosisId;
        this.description = description;
        this.prescription = prescription;
        this.dateRecorded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String toFileString() {
        return diagnosisId + "|" + description + "|" + prescription + "|" + dateRecorded;
    }
}
