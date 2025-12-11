package com.hospital.service;

import com.hospital.model.Employee;
import com.hospital.model.IncidentReport;
import com.hospital.model.PatientRecord;
import com.hospital.repository.impl.IncidentReportRepository;
import com.hospital.repository.impl.PatientFileManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IncidentReportService {

    private final IncidentReportRepository repo;
    private final PatientFileManager patientFiles;

    public IncidentReportService(
            IncidentReportRepository repo,
            PatientFileManager patientFiles) {

        this.repo = repo;
        this.patientFiles = patientFiles;
    }

    public boolean createIncident(String patientId, String reporterId, String description) {

        PatientRecord patientRecord = patientFiles.getPatientRecord(patientId);
        if (patientRecord == null) {
            throw new IllegalArgumentException("Invalid patient ID.");
        }

        Employee reporter = new Employee();
        reporter.setEmployeeId(reporterId);

        if (reporter.getEmployeeId() == null || reporter.getEmployeeId().isEmpty()) {
            throw new IllegalArgumentException("Invalid reporter ID.");
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        IncidentReport report = new IncidentReport(
                null,
                patientId,
                reporter.getEmployeeId(),
                description,
                timestamp
        );

        String id = repo.save(report);
        return (id != null);
    }

    public IncidentReport getIncident(String id) {
        return repo.findById(id);
    }
}






